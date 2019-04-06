package com.payswitch.iso.connection;

import com.payswitch.iso.ConnectorConfiguration;

public class ClientConfiguration extends ConnectorConfiguration {

    /**
     * Default client reconnect interval in milliseconds.
     */
    private static final int DEFAULT_RECONNECT_INTERVAL = 100;

    private int reconnectInterval;

 

    private ClientConfiguration(Builder builder) {
        super(builder);
        this.reconnectInterval = builder.reconnectInterval;
    }

    public static ClientConfiguration.Builder newBuilder() {
        return new ClientConfiguration.Builder();
    }

    public static ClientConfiguration getDefault() {
        return newBuilder().build();
    }

    /**
     * Client reconnect interval in milliseconds.
     *
     * @return interval between reconnects, in milliseconds.
     */
    public int getReconnectInterval() {
        return reconnectInterval;
    }



    public static class Builder extends ConnectorConfiguration.Builder<Builder> {
        private int reconnectInterval = DEFAULT_RECONNECT_INTERVAL;

        public Builder reconnectInterval(int reconnectInterval) {
            this.reconnectInterval = reconnectInterval;
            return this;
        }


        public ClientConfiguration build() {
            return new ClientConfiguration(this);
        }
    }
}
