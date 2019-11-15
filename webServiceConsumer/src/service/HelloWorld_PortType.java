/**
 * HelloWorld_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package service;

public interface HelloWorld_PortType extends java.rmi.Remote {
    public java.lang.String sayTitle(java.lang.String from) throws java.rmi.RemoteException;
    public java.lang.String sayBody(java.lang.String other) throws java.rmi.RemoteException;
    public java.lang.String sayAll(java.lang.String title, java.lang.String body) throws java.rmi.RemoteException;
}
