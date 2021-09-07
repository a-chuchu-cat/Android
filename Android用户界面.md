<h2><font color="blue">Android用户界面</font></h2>

<h4>1.1 用户界面基础</h4>

用户界面（User Interface，UI)是系统和用户之间进行信息交换的媒介，实现信息的内部形式与人类可以接受形式之间的转换

在计算机出现早期，批处理界面（1945-1968）和命令行界面（1969-1983）得到广泛的使用

目前，流行图像用户界面（Graphical User Interface，GUI），采用图形方式与用户进行交互的界面

未来的用户界面将更多的运用虚拟现实技术，使用户能够摆脱键盘与鼠标的交互方式，而通过动作、语言，甚至是脑电波来控制计算机



设计手机用户界面应解决的问题：

​	•需要界面设计与程序逻辑完全分离，这样不仅有利于他们的并行开发，而且在后期修改界面时，也不用再次修改程序的逻辑代码

​	•根据不同型号手机的屏幕解析度、尺寸和纵横比各不相同，自动调整界面上部分控件的位置和尺寸，避免因为屏幕信息的变化而出现显示错误

​	•能够合理利用较小的屏幕显示空间，构造出符合人机交互规律的用户界面，避免出现凌乱、拥挤的用户界面

​	•Android已经解决了前两个问题，使用XML文件描述用户界面；资源文件独立保存在资源文件夹中；对用户界面描述非常灵活，允许不明确定义界面元素的位置	和尺寸，仅声明界面元素的相对位置和粗略尺寸



<h4>1.2 View和ViewGroup</h4>

<b>Android用户界面框架采用MVC（Model-View-Controller）模型</b>

​	控制器（Controller）处理用户输入

​	视图（View）显示用户界面和图像

​	模型（Model）保存数据和代码

![image-20210906200328293](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906200328293.png)

<b>Android用户界面框架采用视图树（View Tree）模型</b>

由View和ViewGroup构成

<b>View是最基本的可视单元</b>

​	存储了屏幕上特定矩形区域内所显示内容的数据结构

​	实现界面绘制、焦点变化、用户输入和界面事件处理等

​	重要基类，所有在界面上的可见元素都是View的子类

<b>ViewGroup是一种能够承载含多个View的显示单元</b>

​	承载界面布局

​	承载具有原子特性的重构模块

![image-20210906200457585](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906200457585.png)

View（微件）通常用于绘制用户可看到并与之交互的内容。ViewGroup 则是不可见的容器

依据视图树的结构从上至下绘制每一个界面元素

每个元素负责对自身的绘制，如果元素包含子元素，该元素会通知其下所有子元素进行绘制

<b>视图树</b>

​	视图树由View和ViewGroup构成

​	View是界面的最基本的可视单元

​	View也是一个重要的基类，所有在界面上的可见元素都是View的子类

<b>ViewGroup是一种能够承载含多个View的显示单元</b>

​	ViewGroup功能：一个是承载界面布局，另一个是承载具有原子特性的重构模块

<b>MVC模型</b>

​	MVC模型中的控制器能够接受并响应程序的外部动作，如按键动作或触摸屏动作等

​	控制器使用队列处理外部动作，每个外部动作作为一个独立的事件被加入队列中，然后Android用户界面框架按照“先进先出”的规则从队列中获取事件，并将这	个事件分配给所对应的事件处理函数

<b>单线程用户界面</b>

​	控制器从队列中获取事件和视图在屏幕上绘制用户界面，使用的都是同一个线程

​	特点：处理函数具有顺序性，能够降低应用程序的复杂程度，同时也能减低开发的难度

​	缺点：如果事件处理函数过于复杂，可能会导致用户界面失去响应



<h4>1.3 界面布局</h4>

界面布局是用户界面结构的描述，定义了界面中所有的元素、结构和相互关系。

在传统的使用C/C++语言编写的Windows程序中，使用明确的位置参数来对界面元素进行定位

![image-20210906201127262](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906201127262.png)

•优点：精确

•缺点：计算复杂，不能自适应，必须编写代码调整



声明Android程序的界面布局有两种方法：

1、使用XML文件描述界面布局（推荐使用）

2、在程序运行时动态添加或修改界面布局

既可以独立使用任何一种声明界面布局的方式，也可以同时使用两种方式

<b>使用XML文件声明界面布局的优势</b>

​	将程序的表现层和控制层分离，修改用户界面时，无需更改程序的源代码

​	可通过“可视化编辑器(Design)” 直接查看用户界面，有利于加快界面设计的过程

![image-20210906201926717](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906201926717.png)



Android界面布局包括:

​	•线性布局

​	•表格布局

​	•帧布局

​	•网格布局

​	•相对布局

​	•绝对布局

​	•约束布局



<h5>1.3.2 线性布局LinearLayout</h5>

在线性布局中，所有的子元素都按照垂直或水平的顺序在界面上排列

​	•如果垂直排列，则每行仅包含一个界面元素

​	•如果水平排列，则每列仅包含一个界面元素

![image-20210906202043471](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906202043471.png)

```
<!--
命名空间(namespace)
XML 命名空间提供避免元素命名冲突的方法。
打个比方,A学校有名学生叫做林小明,B学校也有名学生叫林小明,那我们如何识别这两名拥有相同名字的同学呢?这时候命名空间就派上用场了。A和B此时就可以被当成是命名空间了。也就是说，命名空间里面存放的是特定属性的集合,

1、android
xmlns:android=”http://schemas.android.com/apk/res/android”
在Android布局文件中我们都必须在根元素上定义这样一个命名空间,接下来对这行代码进行逐一讲解：
xmlns:即xml namespace，声明我们要开始定义一个命名空间了
android：称作namespace-prefix，它是命名空间的名字
http://schemas.android.com/apk/res/android：这看起来是一个URL，但是这个地址是不可访问的。实际上这是一个URI(统一资源标识符),所以它的值是固定不变的,相当于一个常量)。
有了他，就会提示你输入什么，也可以理解为语法文件。
使用这行代码，我们就可以引用命名空间中的属性
在这个布局中,只要以android:开头的属性便是引用了命名空间中的属性
android是赋予命名空间一个名字,就跟我们平时在定义变量一样

2、tools
xmlns:tools=”http://schemas.android.com/tools”
2.1、tools只作用于开发阶段
我们可以把他理解为一个工具(tools)的命名空间,它作用于开发阶段,当app被打包时,所有关于tools属性将都会被摒弃掉！
例如,基本上在android命名空间内的属性,我们想在编写代码阶段测试某个组件在屏幕上的效果,而当app安装到手机上时，摒弃掉这条代码,那么我们就可以用tools命名空间来代替掉android:

2.2、tools:context开发中查看Activity布局效果
当我们设置一个Activity主题时,是在AndroidManifest.xml中设置,而主题的效果又只能运行后在Activtiy中显示
使用context属性, 可以在开发阶段中看到设置在Activity中的主题效果
tools:context=”.MainActivity”
在布局中加入这行代码,就可以在design视图中看到与MainActivity绑定主题的效果。

3、自定义命名空间
如果使用DataBinding会在xml用到 app属性，其实这是个自定义命名空间。
xmlns:app=”http://schemas.android.com/apk/res-auto”
实际上也可以这么写：
xmlns:app=”http://schemas.android.com/apk/res/完整的包名”
在res/后面填写包名即可。但是，在Android Studio2.0上，是不推荐这么写的，所以建议大家还是用第一种的命名方法。
-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="right"
    tools:context=".MainActivity">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="ok"
        android:id="@+id/ButtonOk"
        android:layout_alignParentTop="true"
        android:layout_alignParentStart="true" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="cancel"
        android:id="@+id/ButtonCancel"
        android:layout_below="@+id/ButtonOk"
        android:layout_alignStart="@+id/ButtonOk" />
</LinearLayout>
<!--
android:orientation="vertical"
　　　orientation:布局的排列方向，值可以是vertical（垂直）和horizontal（水平），如果忽略这个属性，默认是horizontal
android:layout_gravity="center"
　　　layout_gravity:元素的对齐方向，常用的有right，left，center等，有多个值可以选择
android:id="@+id/idName"
　　　android:id: 为元素添加一个独一无二的身份标识。
     当我们第一次为一个元素添加一个身份标识的时候，就需要用到+号，这样，程序编译的时候，就会在gen目录下的R.java文件中生成一个资源ID，
　　　以后我们就可以通过这个ID引用这个标签中的内容。
　　　除了添加自己的ID名称之外，我们还可以使用android内置的ID。这个时候+号就不需要了，如：
　　　@android:id/list、@android:id/empty
　　　这样，我们定义的元素就会使用android.R类中预先定义的ID值
-->
```

<b><font color="red">组件必须指定宽度和高度</font></b>

每个组件都必须有android:layout_width（宽度），android:layout_height（高度）属性，其值可以是定值(例如“10dip”或“14.5sp”)，但是更常用的是根据容器或内容变化而变化

![image-20210906211535574](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906211535574.png)

![image-20210906211559794](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906211559794.png)

<b>字符串</b>

为减少耦合性，建议字符串内容定义在strings.xml中，其它需要字符串的地方引用strings.xml中定义好的字符串资源。这样当文本内容需要改变的时候，只需要修改strings.xml就可以了，不必修改牵涉到该字符串的所有文件，减少工作量，更重要的是减少出错几率。

<b>在资源文件中定义字符串，在他处引用</b>

修改res/values/strings.xml如下所示

![image-20210906211818588](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906211818588.png)

![image-20210906211847239](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906211847239.png)

<h5>2.3.3 表格布局TableLayout</h5>

表格布局（TableLayout）是一种常用的界面布局，通过指定行和列将界面元素添加到表格中

TableLayout继承自LinearLayout，可以将表格布局看作是多行的线性布局

​	•网格的边界对用户是不可见的

​	•表格布局支持嵌套

​	•可以将表格布局放置在表格布局的表格中

​	•可以在表格布局中添加其他界面布局，例如线性布局、相对布局等

![image-20210906212043491](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906212043491.png)

```
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity" >
    <Button
        android:id="@+id/ButtonUser"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="按钮" />
    <TableRow
        android:layout_width="match_parent"
        android:layout_height="match_parent" >
        <Button
            android:id="@+id/ButtonPrint"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Print" />
        <Button
            android:id="@+id/ButtonExit"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Exit" />
    </TableRow>
</TableLayout>
```

<h5>1.3.4 框架布局</h5>

框架布局（FrameLayout）是最简单的界面布局，是用来存放一个元素的空白空间，且子元素的位置是不能够指定的，只能够放置在空白空间的左上角

如果有多个子元素，后放置的子元素将遮挡先放置的子元素

```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schema.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity" >
    <TextView
        android:id="@+id/MyText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="MyText" />
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/HelloWorld"
        android:text="HelloWorld" />
</FrameLayout>
```

![image-20210906221254754](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906221254754.png)

<hr />

# Android开发：文本控件详解——TextView基本属性

新建的Android项目初始自带的Hello World!其实就是一个TextView。

<h4>一、各项属性</h4>

![image-20210906231607408](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906231607408.png)

**1、对于layout_width和layout_height：**

（1）、wrap_content：控件的大小根据里面的内容大小而定，内容越小，则空间越小，反之亦然。

![image-20210906231823856](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906231823856.png)

（2)、match_parent：控件大小填满整个父容器：

![image-20210906231917391](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906231917391.png)

**2、对于text：**

将字符串放置在value文件夹的strings.xml文件下：

![image-20210906232030272](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906232030272.png)

在activity_main.xml中使用@string/*来调用这个字符串资源，“*”号代表string定义的name:

![image-20210906232105567](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210906232105567.png)

**（1）、textColor：字体颜色，将颜色配置在colors.xml文件中：**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426090300943-1675062636.png)

activity_main.xml中，用@color/*进行调用：

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426090411730-746526748.png)

结果如下：

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426111719160-853444686.png)

**（2）、****textSize：设置字体大小。**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112146377-414770392.png)

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112124832-2124770218.png)

　　**扩展：**设置大小的单位（字体，也包括控件大小）

　　**dp:** 设备独立像素，不同设备有不同的显示效果,这个和设备硬件有关，不依赖像素。 

　　**px:** 像素，在 不同设备显示的效果相同。 

　　**pt:** 标准的长度单位，简单易用，单位换算1pt＝1/72英寸。

　　**sp:** 用于字体显示。

**（3）、textStyle：三种选择。**

normal（无任何效果，常规）：

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112236927-77928473.png)

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112124832-2124770218.png)

bold（文字加粗）：

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112320487-588183374.png)

italic（字体倾斜）：

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112351744-497516019.png)

**（4）、gravity：设置对齐方式：**

常用的对齐方式有：　　

**center：居中对齐，位于容器横向和纵向的中央**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426092033819-1894934042.png)

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112502676-1484823764.png)

**left：向左对齐，位于容器左边**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112535792-1504613676.png)

**right：向右对齐，位于容器右边**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112615590-655003476.png)

**bottom:向底对齐，位于容器底部**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112704062-565141114.png)

**top：向顶对齐，位于容器顶部**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426112535792-1504613676.png)

**center_vertical：位置置于容器的纵向中央部分**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426114507672-1792536791.png)

**center_horizontal：位置置于容器的横向中央部分**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426114536397-1135532854.png)

使用两个值叠加定位，用“|”进行分割，此下为right|top的效果：

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426104448382-471631298.png)

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426115038986-63026568.png)

**（5)、background：背景颜色或背景图片**

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426100409604-209623397.png)

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426115223396-1494001740.png)

**（6）、shadow：文字阴影效果**

**shadowColor**属性用来设置阴影颜色，颜色可以再colors.xml中预先配置；

**shadowRadius**属性设置模糊程度，数值越大，阴影就越模糊；

**shadowDx属性**设置在水平方向上的偏移量，数值越大，则阴影越向右移动；

**shadowDy属性**设置在垂直方向上的偏移量，数值越大，则阴影越向下移动。

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426110653995-1279390875.png)

![img](https://img2018.cnblogs.com/blog/1198161/201904/1198161-20190426110619657-770162355.png)

**（7)、autoLink：链接类型**

**none：**表示不进行任何匹配，默认。

**web：**表示匹配Web Url：http://www.baidu.com会成为可单击跳转的超链接。

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506144509151-1978350648.png)

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506150216642-2086055658.png)

**email：**

表示匹配邮件地址：邮件地址为584224xxx@163.com会成为可单击的超链接。

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506150113278-1448287962.png)

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506150130827-1012141953.png)

**phone：**

表示匹配电话号码：点击号码10086会跳到拨号界面。

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506150011039-724536385.png)

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506145831998-1220445819.png)

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506145916015-1325455940.png)

**map：**表示匹配地图地址。

**all：**表示将会匹配web、email、phone、map所有类型。

**android：textColorLink可以修改链接字体的颜色。**

**（8）、drawableTop（\**drawableLeft、\**\**drawableRight、\**\**drawableBottom\**）:在TextView的上（左、右、下方放置一个drawable（图片等））**

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506151731563-874720034.png)

![img](https://img2018.cnblogs.com/blog/1198161/201905/1198161-20190506151909458-22658361.png)

<hr />

<h4>1.3.5 相对布局RelativeLayout</h4>

相对布局（RelativeLayout）是一种非常灵活的布局方式，能够通过指定界面元素与其他元素的相对位置关系，确定界面中所有元素的布局位置

特点：能够最大程度保证在各种屏幕尺寸的手机上正确显示界面布局

![image-20210907075034911](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907075034911.png)

```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/UserId"
        android:text="User Id"
        android:layout_alignParentTop="true"
        android:layout_alignParentStart="true"
        android:paddingTop="10dp"
        android:paddingBottom="10dp" />
    <EditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="UserId"
        android:id="@+id/editViewUserId"
        android:layout_toEndOf="@+id/UserId"
        android:layout_alignTop="@+id/UserId" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/UserPassword"
        android:text="User Password"
        android:paddingTop="10dp"
        android:paddingBottom="10dp"
        android:layout_below="@+id/UserId"
        android:layout_alignParentStart="true" />
    <EditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/editViewUserPassword"
        android:hint="UserPassword"
        android:layout_alignTop="@+id/UserPassword"
        android:layout_toEndOf="@+id/UserPassword" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/ButtonOk"
        android:text="OK"
        android:layout_below="@+id/editViewUserPassword"
        android:layout_toStartOf="@+id/ButtonCancel" />
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/ButtonCancel"
        android:text="Cancel"
        android:layout_alignParentEnd="true"
        android:layout_alignTop="@+id/ButtonOk" />
</RelativeLayout>
```

<hr />

<h4>RelativeLayout布局属性</h4>

<h5>1.1 与parent相对的属性</h5>

android:layout_alignParentTop
表示widget的顶部和Container的顶部对齐。

android:layout_alignParentBottom
表示widget的底部和Container的底部对齐。

android:layout_alignParentStart
表示widget的左边和Container的起始边缘对齐。

android:layout_alignParentEnd
表示widget的左边和Container的结束边缘对齐。

android:layout_alignParentLeft
表示widget的左边和Container的左边对齐。

android:layout_alignParentRight
表示widget的右边和Container的右边对齐。

android:layout_centerInParent
表示widget处于Container平面上的正中间。

<h5>1.2 与widget相对的属性</h5>

android:layout_above
表示该widget位于参数值标识的widget的上方。

android:layout_below
表示该widget位于参数值标识的widget的下方。

android:layout_alignTop
表示该widget的顶部参数值标识的widget的顶部对齐。

android:layout_alignBottom
表示该widget的底部与参数值标识的widget的底部对齐。

android:layout_alignStart
表示该widget的起始边缘与参数值标识的widget的起始边缘对齐。

android:layout_alignEnd
表示该widget的结束边缘与参数值标识的widget的结束边缘对齐。

android:layout_alignLeft
表示该widget的左边与参数值标识的widget的左边对齐。

android:layout_alignRight
表示该widget的右边参数值标识的widget的右边对齐。

android:layout_centerHorizontal
表示widget处于Container水平方向上的中间。

android:layout_centerVertical
表示widget处于Container垂直方向上的中间。

android:layout_toLeftOf
表示该widget位于参数值标识的widget的左方。

android:layout_toRightOf
表示该widget位于参数值标识的widget的右方。

android:layout_toStartOf
表示该widget结束边缘与参数值标识的widget的起始边缘对齐

android:layout_toEndOf
表示该widget起始边缘与参数值标识的widget的结束边缘对齐

<hr />

<h4>1.3.6 绝对布局AbsoluteLayout</h4>

绝对布局（AbsoluteLayout）能通过指定界面元素的坐标位置，来确定用户界面的整体布局

绝对布局是一种不推荐使用的界面布局，因为通过X轴和Y轴确定界面元素位置后，Android系统不能够根据不同屏幕对界面元素的位置进行调整，降低了界面布局对不同类型和尺寸屏幕的适应能力

<h4>1.3.7 网格布局</h4>

将用户界面划分为网格，界面元素可随意摆放在网格中

网格布局比表格布局（TableLayout）在界面设计上更加灵活，在网格布局中界面元素可以占用多个网格，而在表格布局却无法实现，只能将界面元素指定在一个表格行（TableRow）中，不能跨越多个表格行。

为网格布局添加组件时，组件会按照水平位置从左到右依次排列。如果制定了列数的话则会自动换行。如果用户需要组件在特定位置显示，则需要使用android:layout_row和android:layout_column指定其在网格中的具体行列位置。

如果界面需要跨行或跨列，则使用网格布局非常方便。

<hr />

<h4>GridLayout属性介绍</h4>

<h5>本身属性</h5>

android:columnCount
说明：GridLayout的最大列数

android:rowCount
说明：GridLayout的最大行数

<h5>子元素属性</h5>
android:layout_column
说明：显示该子控件的列，例如android:layout_column=”0”,表示当前子控件显示在第1列，android:layout_column=”1”,表示当前子控件显示在第2列。
android:layout_columnSpan
说明：该控件所占的列数，例如android:layout_columnSpan=”2”,表示当前子控件占两列。

android:layout_row
说明：显示该子控件的行，例如android:layout_row=”0”,表示当前子控件显示在第1行，android:layout_row=”1”,表示当前子控件显示在第2行。

android:layout_rowSpan
说明：该控件所占的列数，例如android:layout_rowSpan=”2”,表示当前子控件占两行。

<hr />

<h4>1.3.8约束布局</h4>

复杂UI可能会出现布局嵌套过多的问题，嵌套得越多，设备绘制视图所需的时间和计算功耗也就越多，而约束布局ConstraintLayout就是为了解决布局嵌套过多的问题，以灵活的方式定位和调整小部件，它比相对布局更灵活，还可按比例约束

![image-20210907092512769](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907092512769.png)

<h5>相对定位</h5>

layout_constraintTop_toTopOf       // 将所需视图的顶部与另一个视图的顶部对齐。  

layout_constraintTop_toBottomOf    // 将所需视图的顶部与另一个视图的底部对齐。 

 layout_constraintBottom_toTopOf    // 将所需视图的底部与另一个视图的顶部对齐。 

 layout_constraintBottom_toBottomOf // 将所需视图的底部与另一个视图的底部对齐。 

 layout_constraintLeft_toTopOf      // 将所需视图的左侧与另一个视图的顶部对齐。 

 layout_constraintLeft_toBottomOf   // 将所需视图的左侧与另一个视图的底部对齐。 

 layout_constraintLeft_toLeftOf     // 将所需视图的左边与另一个视图的左边对齐。  

layout_constraintLeft_toRightOf    // 将所需视图的左边与另一个视图的右边对齐。 

 layout_constraintRight_toTopOf     // 将所需视图的右对齐到另一个视图的顶部。

 layout_constraintRight_toBottomOf  // 将所需视图的右对齐到另一个的底部。 

layout_constraintRight_toLeftOf    // 将所需视图的右边与另一个视图的左边对齐。

 layout_constraintRight_toRightOf   // 将所需视图的右边与另一个视图的右边对齐

<h5>角度定位</h5>

角度定位指的是可以用一个角度和一个距离来约束两个空间的中心

![image-20210907093950058](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907093950058.png)

<h5>居中和偏移</h5>

![image-20210907094308388](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907094308388.png)

<h2>1.4界面常用组件</h2>

<h4>1.4.1 TextView类</h4>

TextView继承自View类，主要用来向用户显示文本内容，一般不允许编辑。

•android:text设置显示文本.

•android:textColor设置文本颜色

•android:textSize设置文字大小，推荐度量单位”sp”，如”15sp”

•android:textStyle设置字形[bold(粗体) 0, italic(斜体) 1, bolditalic(又粗又斜) 2] 可以设置一个或多个，用“|”隔开

![image-20210907094609382](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907094609382.png)

<h4>1.4.2 EditText类</h4>

继承自TextView类，允许用户对文本进行编辑。除了TextView属性外，EditText常用属性如下：

​	•android:cursorVisible:设置光标是否可见，默认可见

​	•android:inputType:文本框类型

​		•text 普通文本。

​		•textCapSentences 显示普通键盘，每句话第一个字母大写。

​		•textAutoCorrect 显示普通键盘，自动纠正拼写错误。

​		•textUri 文本内容为URI，键盘显示时多了“/”字符。

​		•textEmailAddress 文本内容为URI，键盘显示时多了“@”字符

​		•textPassword 普通键盘，文本内容中每个字符用“.”显示，隐藏用户实际内容。

​		•phone 电话号码。

​		•datetime 输入日期和时间。

​		•date 输入日期。

​		•time 输入时间。

<h4>1.4.3 Button类</h4>

Button类继承自TextView类，用户可以对Button组件单击等事件进行处理

id属性，一般是在布局文件中通过**android:id**="@+id/buttonOne"这样的方式来设置，其中“buttonOne” 为按钮的id值，在代码中通过findViewById(R.id.buttonOne)来获得这个Button组件。

text，按钮文本内容

![image-20210907094923204](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907094923204.png)

<b>不同按钮不同外观</b>

如果是普通文本，可以使用Button类。

![image-20210907095041496](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907095041496.png)

如果想要仅包含图标，可以使用ImageButton类.

![image-20210907095117785](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907095117785.png)

如果同时出现图标和文本，则可以使用Button类，指定其android:drawableLeft属性.

![image-20210907095314727](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907095314727.png)

<h5>为按钮设置监听器</h5>

按钮的基本用法是为其设置View.OnClickListener监听器并编写相应的处理代码。


![image-20210907095807303](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907095807303.png)

<b>View.OnClickListener()</b>

View.OnClickListener()是View定义的点击事件的监听器接口，并在接口中仅定义了onClick()函数

当Button从Android界面框架中接收到事件后，首先检查这个事件是否是点击事件，如果是点击事件，同时Button又注册了监听器，则会调用该监听器中的onClick()函数

每个View仅可以注册一个点击事件的监听器，如果使用setOnClickListener()函数注册第二个点击事件的监听器，之前注册的监听器将被自动注销

<b>基于监听的事件处理</b>

在事件监听的处理模型中主要有三类对象：事件源 Event Source、事件 Event、事件监听器 Event Listener

Android的事件处理机制是一种委派式处理方式：组件（事件源）将事件委托给事件监听器处理。

每个组件可以针对事件指定一个监听器，每个监听器也可监听多个事件源。

![image-20210907100243302](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907100243302.png)

基于监听的事件处理模型编程步骤：

1.获取事件源(findViewById)

2.实现事件监听类，该类是一个特殊的Java类，必须实现XxxListener接口

3.将监听器注册到组件上(SetXxxListener,例如SetOnClickListener)



实现事件监听器的几种方式：

1、内部类形式

•监听器类为当前类的内部类

2、外部类形式

•监听器类为当前类的外部类

3、Activity本身作为事件监听器类

•Activity实现监听器接口

4、设置组件的android:onClick的属性

•和Activity本身作为事件监听器类似

5、匿名内部类

•使用匿名内部类创建事件监听器



<b>内部类形式</b>

![image-20210907100632939](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907100632939.png)

<b>外部类形式</b>

![image-20210907100738221](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907100738221.png)

<b>Activity本身作为事件监听器类</b>

![image-20210907100852368](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907100852368.png)

<b>设置组件的android:onClick的属性</b>

![image-20210907100951992](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907100951992.png)

<b>匿名内部类</b>

![image-20210907101043877](C:\Users\MNH\AppData\Roaming\Typora\typora-user-images\image-20210907101043877.png)

•内部类：复用方便；自由访问外部类的界面元素

•外部类：不推荐，除非共享

•Activity：不推荐

•android:onClick ：界面简单

•匿名内部类：简单事件。目前应用广泛
