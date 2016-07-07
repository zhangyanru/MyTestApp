package com.example.myapp.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/7/6.
 * Email:yanru.zhang@renren-inc.com
 *
 *
 * Path指令解析如下所示：

 支持的指令：

 M = moveto(M X,Y) ：将画笔移动到指定的坐标位置
 L = lineto(L X,Y) ：画直线到指定的坐标位置
 H = horizontal lineto(H X)：画水平线到指定的X坐标位置
 V = vertical lineto(V Y)：画垂直线到指定的Y坐标位置
 C = curveto(C X1,Y1,X2,Y2,ENDX,ENDY)：三次贝赛曲线
 S = smooth curveto(S X2,Y2,ENDX,ENDY)
 Q = quadratic Belzier curve(Q X,Y,ENDX,ENDY)：二次贝赛曲线
 T = smooth quadratic Belzier curveto(T ENDX,ENDY)：映射
 A = elliptical Arc(A RX,RY,XROTATION,FLAG1,FLAG2,X,Y)：弧线
 Z = closepath()：关闭路径
 使用原则:

 坐标轴为以(0,0)为中心，X轴水平向右，Y轴水平向下
 所有指令大小写均可。大写绝对定位，参照全局坐标系；小写相对定位，参照父容器坐标系
 指令和数据间的空格可以省略
 同一指令出现多次可以只用一个
 注意，'M'处理时，只是移动了画笔， 没有画任何东西。 它也可以在后面给出上同时绘制不连续线。

 关于这些语法，开发者需要的并不是全部精通，而是能够看懂即可，其它的都可以交给工具来实现。
 */
public class VectorTestActivity extends AppCompatActivity {
    private ImageView imageView;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_test);

        imageView = (ImageView) findViewById(R.id.im);

        drawable = imageView.getDrawable();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }
        });

    }
}
