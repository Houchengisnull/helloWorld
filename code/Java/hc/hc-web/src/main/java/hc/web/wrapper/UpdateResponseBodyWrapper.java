package hc.web.wrapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

@Slf4j
public class UpdateResponseBodyWrapper extends HttpServletResponseWrapper {
    // 缓存
    private ByteArrayOutputStream buffer = null;
    private ServletOutputStream out = null;
    private PrintWriter writer = null;

    @SneakyThrows
    public UpdateResponseBodyWrapper(HttpServletResponse response) {
        super(response);

        this.buffer = new ByteArrayOutputStream();
        this.out = new InnerOutputStream(buffer);
        this.writer = new PrintWriter(new OutputStreamWriter(buffer));

    }

    @SneakyThrows
    public byte[] getResponseBody() {
        out.flush();
        writer.flush();
        return buffer.toByteArray();
    }

    @SneakyThrows
    public void setResponseBody(String responseBody) {
        String characterEncoding = getResponse().getCharacterEncoding();
        byte[] data = responseBody.getBytes(characterEncoding);
        ServletOutputStream output = getResponse().getOutputStream();

        int bufferSize = getResponse().getBufferSize();
        log.debug("response body:{}", responseBody);
        log.debug("response content length:{}", data.length);
        log.debug("response charset:{}", characterEncoding);
        log.debug("response buffer size:{}", bufferSize);
        log.debug("response.getOutputStream():{}", output);
        getResponse().setContentLength(data.length);
        output.write(data);

        buffer.reset();
        out.write(data);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    private class InnerOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream bos;

        private InnerOutputStream(ByteArrayOutputStream bos) {
            this.bos = bos;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {}

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public void write(byte[] b) {
            bos.write(b, 0, b.length);
        }
    }

}
