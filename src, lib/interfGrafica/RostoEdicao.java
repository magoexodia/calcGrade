package interfGrafica;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import banco.BancoDados;
import filtro.Filtragem;

@SuppressWarnings("serial")
public class RostoEdicao extends JFrame {
	protected JTextField cod, nome, req, hora, sem;
	private JCheckBox chckbxConcl;
	public JButton novo, cancel, concluir, limpar;

	public void seta(String linha) {
		sem.setText(linha.split(Filtragem.filtro.sep1)[0]);
		cod.setText(linha.split(Filtragem.filtro.sep1)[1]);
		nome.setText(linha.split(Filtragem.filtro.sep1)[2]);
		req.setText(linha.split(Filtragem.filtro.sep1)[3].replaceAll(
				Filtragem.filtro.sep2, " "));
		hora.setText(linha.split(Filtragem.filtro.sep1)[4]);
		chckbxConcl.setSelected(linha.split(new Filtragem().sep1)[5].equals(Filtragem.filtro.getConcluido()));
	}

	public String junta() {
		/* Apenas seta os campos */
		String linha;
		linha = (sem.getText().isEmpty()?"--":sem.getText())
				+ Filtragem.filtro.sep1
				+ (cod.getText().isEmpty()?"Editar":cod.getText())
				+ Filtragem.filtro.sep1
				+ nome.getText()
				+ Filtragem.filtro.sep1
				+ req.getText().replaceAll(" ", new Filtragem().sep2)
				+ Filtragem.filtro.sep1
				+ hora.getText()
				+ Filtragem.filtro.sep1
				+ (chckbxConcl.isSelected() ? Filtragem.filtro.getConcluido()
						: Filtragem.filtro.getIndefinido());
		return linha.toUpperCase();
	}

	public void novo() {
		// System.out.println(junta());
	}

	public void limpar() {
		cod.setText(null);
		sem.setText(null);
		req.setText(null);
		hora.setText(null);
		nome.setText(null);
		chckbxConcl.setSelected(false);
	}
	
	public boolean salva (boolean anexar){
		if (invalido()){
			return false;
		} else{
			BancoDados.bdados.guardaLinha(junta(), anexar, BancoDados.bdados.getEnder());
		}
		return true;
	}
	
	/* Os dois abaixo apenas fecham a janela. */
	public void cancelar() {
		//this.setVisible(false);
		this.dispose();
	}
	public void concluir() {
		Filtragem.filtro.verReqs(BancoDados.bdados.getEnder());
		cancelar();
	}
	
	public boolean invalido (){
		if (nome.getText().isEmpty() && cod.getText().isEmpty()){
			return true;
		}
		return false;
	}
	
	public RostoEdicao() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.7);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane_1, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane_1.setLeftComponent(splitPane);
		splitPane.setResizeWeight(0.4);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(null);
		
		JLabel lblSemestre = new JLabel("Semestre");
		lblSemestre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSemestre.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSemestre.setBounds(10, 11, 130, 23);
		panel.add(lblSemestre);
		
		JLabel lblCodigoDaDisciplina = new JLabel("codigo da disciplina");
		lblCodigoDaDisciplina.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigoDaDisciplina.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCodigoDaDisciplina.setBounds(10, 50, 130, 23);
		panel.add(lblCodigoDaDisciplina);
		
		JLabel lblNomeDaDisciplina = new JLabel("nome da disciplina");
		lblNomeDaDisciplina.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeDaDisciplina.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNomeDaDisciplina.setBounds(10, 93, 130, 23);
		panel.add(lblNomeDaDisciplina);
		
		JLabel lblPrereqs = new JLabel("pre-requisitos");
		lblPrereqs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrereqs.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPrereqs.setBounds(10, 134, 130, 23);
		panel.add(lblPrereqs);
		
		JLabel lblHorasDeAula = new JLabel("horas de aula");
		lblHorasDeAula.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHorasDeAula.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblHorasDeAula.setBounds(10, 168, 130, 23);
		panel.add(lblHorasDeAula);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(null);
		
		cod = new JTextField();
		cod.setHorizontalAlignment(SwingConstants.LEFT);
		cod.setBounds(10, 51, 266, 20);
		panel_1.add(cod);
		cod.setColumns(12);
		
		nome = new JTextField();
		nome.setHorizontalAlignment(SwingConstants.LEFT);
		nome.setColumns(12);
		nome.setBounds(10, 93, 266, 20);
		panel_1.add(nome);
		
		req = new JTextField();
		req.setHorizontalAlignment(SwingConstants.LEFT);
		req.setColumns(12);
		req.setBounds(10, 134, 266, 20);
		panel_1.add(req);
		
		hora = new JTextField();
		hora.setHorizontalAlignment(SwingConstants.LEFT);
		hora.setColumns(12);
		hora.setBounds(10, 176, 266, 20);
		panel_1.add(hora);
		
		sem = new JTextField();
		sem.setHorizontalAlignment(SwingConstants.LEFT);
		sem.setColumns(12);
		sem.setBounds(10, 11, 266, 20);
		panel_1.add(sem);
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(null);
		
		novo = new JButton("novo");
		novo.setBounds(0, 0, 95, 36);
		novo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				novo();
			}
		});
		panel_2.add(novo);
		
		cancel = new JButton("cancelar");
		cancel.setBounds(382, 0, 100, 36);
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cancelar();
			}
		});
		panel_2.add(cancel);
		
		limpar = new JButton("limpar");
		limpar.setBounds(0, 96, 95, 41);
		limpar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				limpar();
			}
		});
		panel_2.add(limpar);
		
		concluir = new JButton("concluir");
		concluir.setBounds(382, 96, 100, 41);
		concluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				concluir();
			}
		});
		panel_2.add(concluir);
		
		chckbxConcl = new JCheckBox("conclu\u00EDda");
		chckbxConcl.setBounds(104, 53, 238, 23);
		panel_2.add(chckbxConcl);
		chckbxConcl.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxConcl.setFont(new Font("Times New Roman", Font.BOLD, 15));
	}
}
