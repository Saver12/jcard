package com.saver12;

import com.licel.jcardsim.io.CAD;
import com.licel.jcardsim.io.JavaxSmartCardInterface;
import javacard.framework.AID;
import org.junit.Test;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import static org.junit.Assert.assertEquals;

public class Main {

    @Test
    public void smth() {
        System.setProperty("com.licel.jcardsim.terminal.type", "2");
        CAD cad = new CAD(System.getProperties());
        JavaxSmartCardInterface simulator = (JavaxSmartCardInterface) cad.getCardInterface();
        byte[] appletAIDBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        AID appletAID = new AID(appletAIDBytes, (short) 0, (byte) appletAIDBytes.length);
        simulator.installApplet(appletAID, HelloWorldApplet.class);
        simulator.selectApplet(appletAID);
        // test NOP
        ResponseAPDU response = simulator.transmitCommand(new CommandAPDU(0x01, 0x02, 0x00, 0x00));
        assertEquals(0x9000, response.getSW());
        // test hello world from card
        response = simulator.transmitCommand(new CommandAPDU(0x01, 0x01, 0x00, 0x00));
        assertEquals(0x9000, response.getSW());
        assertEquals("Hello world !", new String(response.getData()));
        // test echo
        response = simulator.transmitCommand(new CommandAPDU(0x01, 0x01, 0x01, 0x00, ("Hello javacard world !").getBytes()));
        assertEquals(0x9000, response.getSW());
        assertEquals("Hello javacard world !", new String(response.getData()));

        ///////////////////////////////

        response = simulator.transmitCommand(new CommandAPDU(0x01, 0x09, 0x01, 0x00));
        assertEquals(0x9000, response.getSW());
        byte[] data = response.getData();
        short bufferSize = (short) ((data[0] << 8) | (data[1] & 0xff));
        assertEquals(bufferSize, 260);
    }
}
