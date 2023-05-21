package myapplets;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;

public class AppletProjetIntegre extends Applet {

	/******************** Constantes ************************/ 
	public static final byte CLA_PROJECTAPPLET= (byte) 0xB0; 
	public static final byte INS_GET_POINTS= 0x00; 
	public static final byte INS_SET_POINTS= 0x01; 
	public static final byte INS_RES_POINTS= 0x02;
	public static final byte INS_GET_ID= 0x03; 
	public static final byte INS_SET_ID= 0x04; 
	public static final byte INS_RES_ID= 0x05;
	public static final byte INS_RES_CARD= 0x06;

	/*********************** Variables ***************************/
	private byte id;
	private byte points;

	private AppletProjetIntegre() { 
	    id = 0;
		points = 0;
	}

	public static void install(byte bArray[], short bOffset, byte bLength) throws ISOException { 
		new AppletProjetIntegre().register(); 
	}

	public void process(APDU apdu) throws ISOException { 
		if (selectingApplet()) {
		      return;
		}

		byte[] buffer= apdu.getBuffer();
		if(buffer[ISO7816.OFFSET_CLA]!=CLA_PROJECTAPPLET)
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

		switch(buffer[ISO7816.OFFSET_INS]){
			case INS_GET_POINTS: 
			    buffer[0]=points; 
			    apdu.setOutgoingAndSend((short)0, (short) 1); 
			    break;
			case INS_SET_POINTS: 
			    apdu.setIncomingAndReceive(); 
			    points=buffer[ISO7816.OFFSET_CDATA]; 
			    break;
			case INS_RES_POINTS: 
			    points=0;
			    break;
			case INS_GET_ID: 
			    buffer[0]=id; 
			    apdu.setOutgoingAndSend((short)0, (short) 1); 
			    break;
			case INS_SET_ID: 
			    apdu.setIncomingAndReceive(); 
			    id=buffer[ISO7816.OFFSET_CDATA]; 
			    break;
			case INS_RES_ID: 
			    id=0; 
			    break;
			case INS_RES_CARD: 
			    id=0; 
			    points=0;
			    break;
		}
	}
}
