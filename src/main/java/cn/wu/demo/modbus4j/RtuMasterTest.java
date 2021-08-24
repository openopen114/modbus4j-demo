package cn.wu.demo.modbus4j;

import cn.wu.demo.modbus4j.util.SerialPortWrapperImpl;
import com.serotonin.modbus4j.Modbus;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.msg.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.util.Arrays;
import java.util.Enumeration;

/*
 *
 * 使用 XY-MOD02 透過 modbus 讀取 溫度 濕度 via JAVA
 *
 *
 * */

//REF:https://github.com/wu-boy/modbus4j


public class RtuMasterTest {


    // Modbus Master
    static ModbusMaster master = null;


    public static void main(String[] args) throws Exception {

        // print serial port list
        printSerialProtList();


        // Modbus 初始化 連接
        initModbusConnect();


        int slaveId = 1;

        //讀取溫度
        ReadInputRegistersResponse response = ReadInputRegistersRequest(master, slaveId, 1, 2);

        System.out.println("===> 溫度:" + (double)response.getShortData()[0]/10 );
        System.out.println("===> 濕度:" + (double)response.getShortData()[1]/10 );



        //讀取通訊地址,bud rate
        String hex = "0x0101";
        readHoldingRegisters(master, slaveId, Integer.decode(hex), 2);


    }

 


    /*
     *  print serial port 清單
     *
     * */
    public static void printSerialProtList() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements()) {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            System.out.println("===>" + port.getName());
        }
    }


    /*
     *
     * Modbus 初始化 連接
     *
     * */
    public static void initModbusConnect() throws ModbusInitException {

        //9600 8N1 連接
        SerialPortWrapperImpl wrapper = new SerialPortWrapperImpl("/dev/tty.usbserial-AG0KA7Z1", 9600,
                SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, 0, 0);
        ModbusFactory modbusFactory = new ModbusFactory();
        master = modbusFactory.createRtuMaster(wrapper);
        master.init();


    }


    /*
     *
     * 讀取保存寄存器 (功能碼 03)
     *
     * */
    private static ReadHoldingRegistersResponse readHoldingRegisters(ModbusMaster master, int slaveId, int start, int len) throws Exception {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
        if (response.isException()) {
            System.out.println("===> readHoldingRegisters ERROR " + response.getExceptionMessage());
        } else {
            System.out.println("===> readHoldingRegisters " + Arrays.toString(response.getShortData()));
        }


        return response;
    }


    /*
     *
     * 讀取輸入寄存器 (功能碼 04)
     *
     * */
    private static ReadInputRegistersResponse ReadInputRegistersRequest(ModbusMaster master, int slaveId, int start, int len) throws Exception {
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
        ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);
        if (response.isException()) {
            System.out.println("===> ReadInputRegistersRequest ERROR " + response.getExceptionMessage());
        } else {


            System.out.println("===> ReadInputRegistersRequest " + Arrays.toString(response.getShortData()));
        }

        return response;
    }


    /*
     *
     * 寫入寄存器 (功能碼 06)
     *
     * */
    private static void writeRegister(ModbusMaster master, int slaveId, int offset, int value) throws Exception {
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
        WriteRegisterResponse response = (WriteRegisterResponse) master.send(request);
        if (response.isException()) {
            System.out.println("===> WriteRegisterRequest ERROR " + response.getExceptionMessage());
        } else {
            System.out.println("===> WriteRegisterRequest SUCCESS ");
        }
    }






}
