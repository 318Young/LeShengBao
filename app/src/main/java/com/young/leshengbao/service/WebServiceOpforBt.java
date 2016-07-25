package com.young.leshengbao.service;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
public class WebServiceOpforBt {
    private static LinkedHashMap<String, Object> paramMap;
    public WebServiceOpforBt() {}
    /**
     * @param NameSpace
     * @param Url
     * @param methodname
     * @param paramMap
     * @return
     * @throws IOException
     */
    public SoapObject LoadResult(String NameSpace, String Url, String methodname, Map<String, Object> paramMap) throws IOException {

        SoapObject soapObject = new SoapObject(NameSpace, methodname);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        HttpTransportSE trans = new HttpTransportSE(Url, 6000);
        if (paramMap != null) {
            Iterator<Entry<String, Object>> iter = paramMap.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Object> me = iter.next();
                soapObject.addProperty(me.getKey(), "".equals(me.getValue()) ? null : me.getValue());
            }

        }
        envelope.bodyOut = soapObject;
        envelope.setOutputSoapObject(soapObject);
        envelope.dotNet = true;
        trans.debug = true;
        SoapObject result = null ;
        try {
            trans.call(NameSpace + "." + methodname, envelope);
            System.out.println(trans.requestDump);
//            result = (SoapObject) envelope.bodyIn;
            result =  (SoapObject)envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }



}
