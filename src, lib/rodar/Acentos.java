package rodar;


public class Acentos {
	public static final Acentos acentuar = new Acentos();
	public String aAgudo = "\u00e1";
	public String aCrase = "\u00e0";
	public String eAgudo = "\u00e9";
	public String eCirc = "\u00ea";
	public String iAgudo = "\u00ed";
	public String oAgudo = "\u00f3";
	public String uAgudo = "\u00fa";
	public String aTil = "\u00e3";
	public String oTil = "\u00f0";
	public String aMCrase = "\u00c0";
	public String aMAgudo = "\u00c1";
	public String eMAgudo = "\u00c9";
	public String eMCirc = "\u00ca";
	public String iMAgudo = "\u00cc";
	public String oMAgudo = "\u00d3";
	public String uMAgudo = "\u00da";
	public String aMTil = "\u00c3";
	public String oMTil = "\u00d5";
	public String cedilMin = "\u00e7";
	public String cedilMai = "\u00c7";
	public String copy = "\u00a9";
	public String Regis = "\u00ae";
	public String aOrdin = "\u00aa";
	public String oOrdin = "\u00b0";
	public String maisMenos = "\u00b1";
	

	
	public static void mAchaLetra(int quant) {
		//prin.executa();		
		/*public static String geraCodigoUnicode(char letra)*/ 
		for (int i = 0; i < quant; i++){
		    String hexa = Integer.toHexString( i );

		    String prefix;
		    if( hexa.length() == 1 ) {
		        prefix = "\\u000";
		    } else if( hexa.length() == 2 ) {
		        prefix = "\\u00";
		    } else if( hexa.length() == 3 ) {
		        prefix = "\\u0";
		    } else {
		        prefix = "\\u";
		    }

		    System.out.print((char)i + ": " + prefix + hexa + " \t ");
		    if (i%7 == 0){
		    	System.out.println();
		    }
		}
	}
}
