package org.javatribe.calculator.domain.printer;


import com.zebra.sdk.printer.discovery.DiscoveredPrinter;

public class SlimDiscoPrinter {
    private String address;
    private String dnsName;
    private String firmwareVer;
    private String productName;
    private String serialNumber;

    public SlimDiscoPrinter(DiscoveredPrinter printer) {
        this.setAddress(printer.getDiscoveryDataMap().get("ADDRESS"));
        this.setProductName(printer.getDiscoveryDataMap().get("PRODUCT_NAME"));
        this.setDnsName(printer.getDiscoveryDataMap().get("DNS_NAME"));
        this.setFirmwareVer(printer.getDiscoveryDataMap().get("FIRMWARE_VER"));
        this.setSerialNumber(printer.getDiscoveryDataMap().get("SERIAL_NUMBER"));
    }

    public String getDnsName() {
        return this.dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFirmwareVer() {
        return this.firmwareVer;
    }

    public void setFirmwareVer(String firmwareVer) {
        this.firmwareVer = firmwareVer;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}