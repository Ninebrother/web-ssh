package com.alon.ssh.ws;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionMonitor;
import ch.ethz.ssh2.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by zhangyl on 2019/4/4
 */
public class SSHClient {

    private Connection connection;
    private Session session;

    public SSHClient() {
    }

    /**
     * 用户名密码链接
     *
     * @param host
     * @param port
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public SSHClient connectWithPassWord(String host, int port, String userName, String password) throws Exception {
        Connection connection = new Connection(host, port);
        connection.connect();
        if (!connection.authenticateWithPassword(userName, password)) {
            return null;
        } else {
            return getClient(connection);
        }
    }

    /**
     * SSH免密链接
     *
     * @param host
     * @param port
     * @param user
     * @param privateKey
     * @param password   如果PEM使用了密码，此处必须传
     * @return
     * @throws Exception
     */
    public SSHClient connectWithSSHKey(String host, int port, String user, String privateKey, String password) throws Exception {
        Connection connection = new Connection(host, port);
        connection.connect();
        if (!connection.authenticateWithPublicKey(user, privateKey.toCharArray(), password)) {
            return null;
        } else {
            return getClient(connection);
        }
    }

    public SSHClient getClient(Connection connection) throws IOException {
        SSHClient client = new SSHClient();
        Session session = connection.openSession();
        session.requestPTY("xterm");
        session.startShell();
        client.setConnection(connection);
        client.setSession(session);
        return client;
    }

    public void write(String cmd) throws IOException {
        byte[] bytes = cmd.getBytes(StandardCharsets.UTF_8);
        OutputStream outputStream = this.session.getStdin();
        outputStream.write(bytes, 0, bytes.length);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void close() {
        if (this.session != null) {
            this.session.close();
        }

        if (this.connection != null) {
            this.connection.close();
        }
    }

    public void addListener(ConnectionMonitor connectionMonitor) {
        this.connection.addConnectionMonitor(connectionMonitor);
    }
}
