package com.packa.japp.naivechain;

import com.fasterxml.jackson.annotation.JsonRawValue;

import java.io.Serializable;
import java.util.Objects;

public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    private int index;
    private String previousHash;
    private String timestamp;
    @JsonRawValue
    private Object data;
    private String hash;

    public void Block(int index, String previousHash, String timestamp, Object data, String hash) {
        this.index = index;
        this.previousHash = previousHash.toString();
        this.timestamp = timestamp;
        this.data = data;
        this.hash = hash.toString();
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Block block = (Block) o;
        if (block.getHash() == null || getHash() == null) {
            return false;
        }
        return Objects.equals(getHash(), block.getHash());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHash());
    }

    @Override
    public String toString() {
        return "Block{" +
            "data=" + getData() +
            "}";
    }
}
