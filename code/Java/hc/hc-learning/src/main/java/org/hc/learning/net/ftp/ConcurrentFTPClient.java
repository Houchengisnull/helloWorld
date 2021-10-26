package org.hc.learning.net.ftp;

import java.io.IOException;

public class ConcurrentFTPClient extends QuickFTPClient{

    public ConcurrentFTPClient(String host, int port, String username, String password, String encoding) throws IOException {
        super(host, port, username, password, encoding);
    }
}
