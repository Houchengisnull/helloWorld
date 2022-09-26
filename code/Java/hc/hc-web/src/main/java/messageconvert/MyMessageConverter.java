package messageconvert;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import hc.web.bean.MessageConvertBean;

public class MyMessageConverter extends AbstractHttpMessageConverter<MessageConvertBean>{
	
	public MyMessageConverter() {
		super(new MediaType("application","x-wisely",Charset.forName("UTF-8")));
	}
	
	// 该Converter支持类型
	@Override
	protected boolean supports(Class<?> clazz) {
		return MessageConvertBean.class.isAssignableFrom(clazz);
	}

	// 处理请求数据
	@Override
	protected MessageConvertBean readInternal(Class<? extends MessageConvertBean> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}


	// 处理如何输出数据到response
	@Override
	protected void writeInternal(MessageConvertBean t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
	}

}
