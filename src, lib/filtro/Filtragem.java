package filtro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import banco.BancoDados;

public class Filtragem {
	private final String Aberto = "aberto".toUpperCase(); 
	private final String Concluido = "conclu".toUpperCase();
	private final String EmCurso = "Em cur".toUpperCase();
	private final String Indefinido = "Indefi".toUpperCase();
	public final String extension = ".bancoDeListas"; 
	public final String sep1 = ";"; 
	public final String sep2 = "-"; 
	protected Set<Integer> perc;
	
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
		List<String> arquivo = new BancoDados().pegaObjetoList(caminho);
		
		boolean anexar = false;
		for (String um : arquivo){
			if (um.endsWith(filtro) || um.startsWith(filtro) || um.split(";")[1].startsWith(filtro)){
				new BancoDados().guardaLinha(um, anexar, filtro + extension);
				anexar = true;
			}
		}
	}	

	public void filtraTudo(String caminho) {
		List<String> arquivo = new BancoDados().pegaObjetoList(caminho);
		String f1 = getAberto();
		String f2 = getConcluido();
		String f3 = getIndefinido();
		boolean a = false, b = false, c = false;
		
		for (String um : arquivo){
			if (um.endsWith(f1)){
				new BancoDados().guardaLinha(um, a, f1 + extension);
				a = true;
			} else if (um.endsWith(f2)){
				new BancoDados().guardaLinha(um, b, f2 + extension);
				b = true;
			} else if (um.endsWith(f3)){
				new BancoDados().guardaLinha(um, c, f3 + extension);
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
			new BancoDados().guardaLinha(dois, (tem.indexOf(dois) != 0), filtro + extension);
		}
		
		return tem;
	}
	
	public void verReqs (String caminho){
		List<String> tudo = new BancoDados().pegaObjetoList(caminho);
		filtra(caminho, getConcluido());
		List<String> todo = new BancoDados().pegaObjetoList(getConcluido()+extension);
		String feitos = " ";
		List<String> disc = new ArrayList<String>();
		
		for (String um : todo) {
			feitos = feitos + um.split(sep1)[1] + " ";
		}
		
		todo.clear();
		for (String um : tudo) {
			if (um.endsWith(getConcluido())){
				disc.add(um.replace(um.split(sep1)[5], getConcluido()));
			} else if (um.split(sep1)[3].isEmpty()){
				disc.add(um.replace(um.split(sep1)[5], getAberto()));
			} else if (!um.split(sep1)[3].isEmpty()){
				String[] dois = um.split(sep1)[3].split(sep2);
				int pre = dois.length;
				for (int i = 0; i < pre; i++) {
					if (!feitos.contains(dois[i])) {
						pre = -1;
						break;
					}
				}
				if (pre == -1) {
					disc.add(um.replace(um.split(sep1)[5], getIndefinido()));
				} else {
					disc.add(um.replace(um.split(sep1)[5], getAberto()));
				}
			}
		}
		
		tudo.clear();
		
		for (String um : disc) {
			new BancoDados().guardaLinha(um, !(disc.indexOf(um) == 0), "buffer.txt");
		}
		new BancoDados().copiaArquivo("buffer.txt", caminho);
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
		String[] tudo = new BancoDados().pegaObjeto(caminho);
		List<String> todo = new ArrayList<String>();
		String pres = sep;
		int dis = 0;
		
		if (disc.split(sep1)[3].isEmpty()){
			return (new String[] {"--;------;Sem pre-requisitos;;--;------"});
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
						"Algum dos pre-requisitos n�o consta na sua lista de disciplinas.\n"
								+ "\nFavor adicion�-lo � lista"
								+ "\nou editar esta disciplina"
								+ "\npara que este erro n�o ocorra mais.");
				
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
}