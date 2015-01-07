package filtro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import rodar.Acentos;

import banco.BancoDados;

public class Filtragem {
	private final String Aberto = "1aberto1".toUpperCase(); 
	private final String Concluido = "2conclu2".toUpperCase();
	private final String EmCurso = "3Em cur3".toUpperCase();
	private final String Indefinido = "0Indefi0".toUpperCase();
	public final String extension = ".bListas"; 
	public final String sep1 = ";"; 
	public final String sep2 = "-"; 
	public final String semPre = "--;------;Sem pre-requisitos;;--;------";
	private Set<Integer> perc;
	
	public static final Filtragem filtro = new Filtragem();
	
	public String getAberto() {
		return Aberto;
	}
	public String getConcluido() {
		return Concluido;
	}
	public String getEmCurso() {
		return EmCurso;
	}
	public String getIndefinido() {
		return Indefinido;
	}

	public String pegaDisc (){
		String nova = JOptionPane.showInputDialog("Digite na ordem\nsem,cod,nome,req1-req2-req3,horas");
		System.out.println(nova);
		return nova;
	}
	
	//permite filtrar pelo inicio, final ou pelo 2o peda�o e jogar num arquivo
	public void filtra(String caminho, String filtro) {
		List<String> arquivo = BancoDados.bdados.pegaObjetoList(caminho);
		
		boolean anexar = false;
		for (String um : arquivo){
			if (um.endsWith(filtro) || um.startsWith(filtro) || um.split(sep1)[1].startsWith(filtro)){
				new BancoDados().guardaLinha(um, anexar, filtro + extension);
				anexar = true;
			}
		}
	}	
	public String[] filtra(String filtro, String[] tudo) {
		List<String> objetos = new ArrayList<String>();
		for (int i = 0; i < tudo.length; i++) {
			if (tudo[i].contains(filtro)){
				objetos.add(tudo[i]);
			}
		}
		return ListToArray(objetos);
	}	

	public void filtraTudo(String caminho) {
		List<String> arquivo = BancoDados.bdados.pegaObjetoList(caminho);
		String f1 = getAberto();
		String f2 = getConcluido();
		String f3 = getIndefinido();
		boolean a = false, b = false, c = false;
		
		for (String um : arquivo){
			if (um.endsWith(f1)){
				BancoDados.bdados.guardaLinha(um, a, f1 + extension);
				a = true;
			} else if (um.endsWith(f2)){
				BancoDados.bdados.guardaLinha(um, b, f2 + extension);
				b = true;
			} else if (um.endsWith(f3)){
				BancoDados.bdados.guardaLinha(um, c, f3 + extension);
				c = true;
			}
		}
	}
	
	public List<String> filtra(List<String> lista, String filtro) {
		List<String> tem = new ArrayList<String>();
		
		for (String um : lista){
			if (um.endsWith(filtro) || um.startsWith(filtro) || um.contains(filtro)){
				tem.add(um);
			}
		}
		
		for (String dois : tem) {
			BancoDados.bdados.guardaLinha(dois, (tem.indexOf(dois) != 0), filtro + extension);
		}
		
		return tem;
	}
	
	public void verReqs (String caminho){
		List<String> tudo = BancoDados.bdados.pegaObjetoList(caminho);
		if (tudo == null){
			return;
		}
		filtra(caminho, getConcluido());
		List<String> todo = BancoDados.bdados.pegaObjetoList(getConcluido() + this.extension);
		String feitos = " ";
		List<String> disc = new ArrayList<String>();
		
		if (todo != null) {
			for (String um : todo) {
				feitos = feitos + um.split(this.sep1)[1] + " ";
			}
			todo.clear();
		}
		
		for (String um : tudo) {
			if (um.endsWith(this.getConcluido())){
				disc.add(um.replace(um.split(this.sep1)[5], this.getConcluido()));
			} else if (um.split(this.sep1)[3].isEmpty()){
				disc.add(um.replace(um.split(this.sep1)[5], this.getAberto()));
			} else if (!um.split(this.sep1)[3].isEmpty()){
				String[] dois = um.split(this.sep1)[3].split(this.sep2);
				int pre = dois.length;
				for (int i = 0; i < pre; i++) {
					if (!feitos.contains(dois[i])) {
						pre = -1;
						break;
					}
				}
				if (pre == -1) {
					disc.add(um.replace(um.split(this.sep1)[5], this.getIndefinido()));
				} else {
					disc.add(um.replace(um.split(this.sep1)[5], this.getAberto()));
				}
			}
		}
		
		tudo.clear();
		
		for (String um : disc) {
			BancoDados.bdados.guardaLinha(um, !(disc.indexOf(um) == 0), "buffer.txt");
		}
		BancoDados.bdados.copiaArquivo("buffer.txt", caminho);
	}
	
	public String paraNum (int num){
		String numero = "";
		List<Integer> n = new ArrayList<Integer>();
		int i = num;
		
		do{
			n.add(i % 10);
			i /=10;
		}while ((i%10) > 0 || (i/10) > 0);
		
		for (Integer o : n) {
			switch (o) {
			case 0:
				numero = "0" + numero;
				break;
			case 1:
				numero = "1" + numero;
				break;
			case 2:
				numero = "2" + numero;
				break;
			case 3:
				numero = "3" + numero;
				break;
			case 4:
				numero = "4" + numero;
				break;
			case 5:
				numero = "5" + numero;
				break;
			case 6:
				numero = "6" + numero;
				break;
			case 7:
				numero = "7" + numero;
				break;
			case 8:
				numero = "8" + numero;
				break;
			case 9:
				numero = "9" + numero;
				break;
			default:
				break;
			}

		}
		
		return numero;
	}
	
	public String[] percurso (String caminho, String disc){
		String sep = " ";
		String[] tudo = BancoDados.bdados.pegaObjeto(caminho);
		if (tudo == null){
			return null;
		}
		List<String> todo = new ArrayList<String>();
		String pres = sep;
		int dis = 0;
		
		if (disc.split(sep1)[3].isEmpty()){
			return (new String[] {semPre});
		}
		
		for (int i = 0; i < tudo.length; i++) {
			todo.add(tudo[i].split(sep1)[1]);
			if (tudo[i].split(sep1)[3].isEmpty()){
				pres = pres + "." + sep;
				continue;
			}
			pres = pres + tudo[i].split(sep1)[3] + sep;
			if (disc.equals(tudo[i])){
				dis = i;
			}
		}
		pres = pres.trim();
		
		for (String um : todo) {
			if (pres.contains(um) && (dis != todo.indexOf(um))){
				pres = pres.replaceAll(um, paraNum(todo.indexOf(um)));
			}
		}
		
		//O que � que eu fa�o com isso?
		perc = new HashSet<Integer>();
		listaPre(pres, dis);
		String[] discs = new String[perc.size()];
		int i = 0;
		for (Integer num : perc) {
			if (num == -1){
				JOptionPane.showMessageDialog(null,
						"Algum dos pre-requisitos n"+Acentos.acentuar.aTil+"o consta na sua lista de disciplinas.\n"
								+ "\nFavor adicion"+Acentos.acentuar.aAgudo+"-lo "+Acentos.acentuar.aCrase+" lista"
								+ "\nou editar esta disciplina"
								+ "\npara que este erro n"+Acentos.acentuar.aTil+"o ocorra mais.");
				
				discs[i++] = "1 ou;mais de 1;pre-requisito nao consta na sua lista;!!!;!!!;!!!";
				continue;
			}
			discs[i++] = tudo[num];
		}
		perc.clear();
		todo.clear();
		return discs;
	}
	
	private boolean eCodigo(String termo) {
		try {
			Integer.parseInt(termo);
			return false;
		} catch (java.lang.NumberFormatException e) {
			return true;
		}
	}
	
	public void listaPre (String pre, int loc){
		
		//"." significa vazio, sem pre-reqs
		if (pre.split(" ")[loc].equals(".")){
			return;
		}
		
		//com pre-reqs
		String[] dois = pre.split(" ")[loc].split(sep2);
		for (int i = 0; i < dois.length; i++) {
			if (eCodigo(dois[i])){
				perc.add(-1);
				continue;
			}
			perc.add(Integer.parseInt(dois[i]));
			listaPre(pre, Integer.parseInt(dois[i]));
		}
	}
	

	/*********************************************************************/
	public String[] objeta(String caminho) {
		List<String> ob;
		
		BancoDados.bdados.limpaDuplos(caminho);
		
		if (!caminho.equals(BancoDados.bdados.getEnder())){
			ob = this.filtra(BancoDados.bdados.pegaObjetoList(BancoDados.bdados.getEnder()), caminho.split(this.extension)[0]);
		} else {
			ob = BancoDados.bdados.pegaObjetoList(caminho);			
		}
		
		if (ob.isEmpty()){
			return null;
		}
		
		String[] objetos = new String[ob.size()];
		int i = 0;
		for (String um : ob) {
			String quem = um.split(sep1)[5];
			objetos[i++] = (quem.equals(getAberto())?" +":quem.equals(getConcluido())?"OK":"- ")
					+ " ("
					+ um.split(sep1)[0] 
					+ Acentos.acentuar.oOrdin+"sem) ["
					+ um.split(sep1)[1] 
					+ "] "
					+ um.split(sep1)[2] 
					+ " => {"
					+ um.split(sep1)[3].replaceAll(sep2, ", ")
					+ "}";
		}
		return objetos;
	}
	
	public String[] ListToArray (List<String> ob){
		String[] objetos = new String[ob.size()];
		for (String um : ob) {
			objetos[ob.indexOf(um)] = um;
		}
		return objetos;
	}
	
	public String[] objeta(String[] objetos) {
		for (int j = 0; j < objetos.length; j++) {
			String quem = objetos[j].split(sep1)[5];
			objetos[j] = (quem.equals(getAberto())?" +":quem.equals(getConcluido())?"OK":"- ")
					+ " ("
					+ objetos[j].split(sep1)[0] 
					+ Acentos.acentuar.oOrdin+"sem) ["
					+ objetos[j].split(sep1)[1] 
					+ "] "
					+ objetos[j].split(sep1)[2] 
					+ " => {"
					+ objetos[j].split(sep1)[3].replaceAll(sep2, ", ")
					+ "}";
		}
		return objetos;
	}
}
