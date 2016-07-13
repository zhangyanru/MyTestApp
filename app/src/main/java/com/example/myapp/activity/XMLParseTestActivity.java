package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp.R;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by yanru.zhang on 16/7/12.
 * Email:yanru.zhang@renren-inc.com
 */
public class XMLParseTestActivity extends Activity implements View.OnClickListener{
    private TextView textView;
    private Button dom;
    private Button sax;
    private Button pull;
    private Button writeXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parse);

        textView = (TextView) findViewById(R.id.text_view);
        dom = (Button) findViewById(R.id.dom_parse);
        dom.setOnClickListener(this);
        sax = (Button) findViewById(R.id.sax_parse);
        sax.setOnClickListener(this);
        pull = (Button) findViewById(R.id.pull_parse);
        pull.setOnClickListener(this);
        writeXML = (Button) findViewById(R.id.write_xml);
        writeXML.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dom_parse:
                domParse();
                break;
            case R.id.sax_parse:
                saxParse();
                break;
            case R.id.pull_parse:
                pullParse();
                break;
            case R.id.write_xml:
                writeXml();
                break;
        }
    }

    /**
     * 使用XmlSerializer创建XML文件
     */
    private void writeXml() {
        //别忘记在Mainfest添加权限
        //在SD卡创建名为poem.xml的文件
        File xmlFile = new File(getApplicationContext().getFilesDir().getAbsolutePath()+"/poem.xml");
        try {
            xmlFile.createNewFile();
            Log.d("zyr","path:" + xmlFile.getAbsolutePath());
            FileOutputStream os = new FileOutputStream(xmlFile);
            //创建XmlSerializer对象
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(os,"UTF-8");
            xmlSerializer.startDocument("UTF-8",Boolean.valueOf(true));
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output",true);

            xmlSerializer.startTag(null,"poems");
                xmlSerializer.startTag(null,"poem");
                xmlSerializer.attribute(null,"id","1");
                xmlSerializer.text("你是谁呀");
                xmlSerializer.endTag(null,"poem");

                xmlSerializer.startTag(null,"poem");
                xmlSerializer.attribute(null,"id","2");
                    xmlSerializer.startTag(null,"author");
                    xmlSerializer.text("白居易");
                    xmlSerializer.endTag(null,"author");

                    xmlSerializer.startTag(null,"name");
                    xmlSerializer.text("待填写");
                    xmlSerializer.endTag(null,"name");

                    xmlSerializer.startTag(null,"content");
                    xmlSerializer.text("忘记了");
                    xmlSerializer.endTag(null,"content");
                xmlSerializer.endTag(null,"poem");
            xmlSerializer.endTag(null,"poems");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void pullParse() {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("myfriends.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream == null)
            return;

        StringBuffer stringBuffer = new StringBuffer();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(inputStream,"UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType!= XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        stringBuffer.append("<" + xmlPullParser.getName());
                        for(int i=0;i<xmlPullParser.getAttributeCount();i++){
                            stringBuffer.append(" " + xmlPullParser.getAttributeName(i) + "=" + xmlPullParser.getAttributeValue(i));
                        }
                        stringBuffer.append(">");
                        break;
                    case XmlPullParser.TEXT:
                        stringBuffer.append(xmlPullParser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        stringBuffer.append("</" + xmlPullParser.getName() + ">");
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                }
                eventType = xmlPullParser.next();
            }
            textView.append(stringBuffer.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saxParse() {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("mylaunch.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream == null)
            return;

        final StringBuffer stringBuffer = new StringBuffer();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler defaultHandler = new DefaultHandler(){
                //开始解析文档，即开始解析XML根元素时调用该方法
                @Override
                public void startDocument() throws SAXException {
                    Log.d("zyr","startDocument");
                    super.startDocument();
                }
                //开始解析每个元素时都会调用该方法
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    Log.e("zyr","startElement " + qName);
                    super.startElement(uri, localName, qName, attributes);
                    stringBuffer.append("<" + qName);
                    if(attributes!=null){
                        for(int i=0;i<attributes.getLength();i++){
                            stringBuffer.append(" " + attributes.getQName(i) + "=" + attributes.getValue(i));
                        }
                    }
                    stringBuffer.append(">");
                }
                //解析到每个元素的内容时会调用此方法
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    Log.d("zyr","characters " + new String(ch,start,start+length));
                    super.characters(ch, start, length);
                    stringBuffer.append(new String(ch,start,start+length));
                }
                //每个元素结束的时候都会调用该方法

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    Log.e("zyr","endElement " + qName);
                    super.endElement(uri, localName, qName);
                    stringBuffer.append("</" + qName + ">");
                }
                //结束解析文档，即解析根元素结束标签时调用该方法
                @Override
                public void endDocument() throws SAXException {
                    Log.d("zyr","endDocument");
                    super.endDocument();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(stringBuffer.toString());
                        }
                    });
                }
            };
            saxParser.parse(inputStream,defaultHandler);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 使用递归解析一个XML文档
     *
     */
    private void domParse() {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("myuser.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream == null)
            return;
        StringBuffer stringBuffer = new StringBuffer();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element rootElement = document.getDocumentElement();//users
            stringBuffer.append(parseElement(rootElement));
            textView.setText(stringBuffer.toString());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String parseElement(Element element) {
        StringBuffer stringBuffer = new StringBuffer();
        String tagName = element.getNodeName();
        stringBuffer.append("<" + tagName);
        // element元素的所有属性构成的NamedNodeMap对象，需要对其进行判断
        NamedNodeMap attributeMap = element.getAttributes();
        // 如果存在属性，则打印属性
        if (null != attributeMap) {
            for (int i = 0; i < attributeMap.getLength(); i++) {
                // 获得该元素的每一个属性
                Attr attr = (Attr) attributeMap.item(i);
                // 属性名和属性值
                String attrName = attr.getName();
                String attrValue = attr.getValue();
                // 注意属性值需要加上引号，所以需要\转义
                stringBuffer.append(" " + attrName + "=\"" + attrValue + "\"");
            }
        }
        // 关闭标签名
        stringBuffer.append(">");
        // 至此已经打印出了元素名和其属性
        // 下面开始考虑它的子元素
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            // 获取每一个child
            Node node = children.item(i);
            // 获取节点类型
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                // 如果是元素类型，则递归输出
                stringBuffer.append(parseElement((Element) node));
            } else if (nodeType == Node.TEXT_NODE) {
                // 如果是文本类型，则输出节点值，及文本内容
                stringBuffer.append(node.getNodeValue());
            } else if (nodeType == Node.COMMENT_NODE) {
                // 如果是注释，则输出注释
                stringBuffer.append("<!--");
                Comment comment = (Comment) node;
                // 注释内容
                String data = comment.getData();
                stringBuffer.append(data);
                stringBuffer.append("-->");
            }
        }
        // 所有内容处理完之后，输出，关闭根节点
        stringBuffer.append("</" + tagName + ">");
        return stringBuffer.toString();
    }
}
