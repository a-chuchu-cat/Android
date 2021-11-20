package com.example.urlconnection;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUpload {
    private static final String IMGUR_CLIENT_ID = "2019011163";
    /**
     * 媒体类型（通常称为 Multipurpose Internet Mail Extensions 或 MIME 类型 ）是一种标准，用来表示文档、文件或字节流的性质和格式。
     *
     * 浏览器通常使用MIME类型（而不是文件扩展名）来确定如何处理URL，因此Web服务器在响应头中添加正确的MIME类型非常重要。
     * 如果配置不正确，浏览器可能会曲解文件内容，网站将无法正常工作，并且下载的文件也会被错误处理。
     *
     * type/subtype
     * MIME的组成结构非常简单；由类型与子类型两个字符串中间用'/'分隔而组成。不允许空格存在。type 表示可以被分多个子类的独立类别。subtype 表示细分后的每个类型。
     *
     * MediaType指的是要传递的数据的MIME类型，MediaType对象包含了三种信息：type 、subtype以及charset，一般将这些信息传入parse()方法中，这样就可以解析出MediaType对象，
     * 比如 "text/x-markdown; charset=utf-8" ，type值是text，表示是文本这一大类；/后面的x-markdown是subtype，表示是文本这一大类下的markdown这一小类；
     * charset=utf-8 则表示采用UTF-8编码。
     *
     */
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final OkHttpClient client = new OkHttpClient();

    private static String emotion="";

    /**
     * 创建okHttpClient对象
     * 创建Request对象
     * 把请求封装成任务，得到Call对象
     * 以同步或异步的方法去执行请求，将call加入调度队列，任务执行完成，在CallBack中得到回调(异步)。同步通过call.execute().body().string();得到返回结果
     */
    public static String run(File f) throws Exception {
        final File file=f;

        new Thread() {
            @Override
            public void run() {
                //子线程需要做的工作

                /**
                 * setType():Set the MIME type. Expected values for type are MIXED (the default), ALTERNATIVE, DIGEST, PARALLEL and FORM.
                 * addFromDataPart():Add a form data part to the body.
                 * RequestBody.create():Returns a new request body that transmits the content of file.
                 */
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", "picture")
                        .addFormDataPart("picture", UUID.randomUUID().toString()+".png",
                                RequestBody.create(MEDIA_TYPE_PNG, file))
                        .build();
                //设置为自己的ip地址

                /**
                 * header():Sets the header named name to value. If this request already has any headers with that name, they are all replaced.
                 * url():Sets the URL target of this request.
                 */
                Request request = new Request.Builder()
                        .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                        .url("http://211.68.35.79:5001/predict")
                        .post(requestBody)
                        .build();
                try {
                    /**
                     * 同步--Call有一个execute()方法，可以直接调用call.execute()返回一个Response。
                     */
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    emotion=response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return emotion;
    }
}
