package com.xianyu.getlecture;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MySAXHandler extends DefaultHandler {

	private boolean mIsTitleTag = false;  
    private boolean mIsSalaryTag = false;  
    private boolean mIsBirthTag = false;  
    private String mResult = "";  
    
    // 打开 xml 文档的回调函数  
    @Override  
    public void startDocument() throws SAXException {  
        // TODO Auto-generated method stub  
        super.startDocument();  
    }  
      
    // 关闭 xml 文档的回调函数  
    @Override  
    public void endDocument() throws SAXException {  
        // TODO Auto-generated method stub  
        super.endDocument();  
    }  
      
    // 一发现元素开始标记就回调此函数  
    @Override  
    public void startElement(String uri, String localName, String qName,  
            Attributes attributes) throws SAXException {  
        if (localName == "event")  
            mIsTitleTag = true;  
        else if (localName == "salary")  
            mIsSalaryTag = true;  
        else if (localName == "dateOfBirth")  
            mIsBirthTag = true;  
        else if (localName == "employee")  
            mResult += "\nname:" + attributes.getValue("name");      
    }  
  
    // 一发现元素结束标记就回调此函数  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        if (localName == "event")  
            mIsTitleTag = false;  
        else if (localName == "salary")  
            mIsSalaryTag = false;  
        else if (localName == "dateOfBirth")  
            mIsBirthTag = false;  
    }  
  
    // 一发现元素值或属性值就回调此函数  
    @Override  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
        if (mIsTitleTag)  
            mResult += new String(ch, start, length);  
        else if (mIsSalaryTag)  
            mResult += " salary:" + new String(ch, start, length);  
        else if (mIsBirthTag)  
            mResult += " dateOfBirth:" + new String(ch, start, length);  
    }  
      
    public String getResult(){  
        return mResult;  
    }  
	
	

}
