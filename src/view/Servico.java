package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import model.DAO;
import util.Validador;

@SuppressWarnings("serial")
public class Servico<txtNomeCli> extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtOS;
	private JTextField txtBicicleta;
	private JTextField txtDefeito;
	private JTextField textDiagnostico;
	private JTextField txtCliente;
	private JTextField textPeca;

	public static void main(String[] args) {

		try {
			@SuppressWarnings("rawtypes")
			Servico dialog = new Servico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	@SuppressWarnings("rawtypes")
	private JComboBox cboStatus;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnLimpar;
	private JButton btnDelete;
	private JLabel lblNewLabel_10;
	private JButton btnBuscar;
	private JTextField txtNomeCli;
	@SuppressWarnings("rawtypes")
	private JList listaClientes;
	private JScrollPane scrollPane;
	private JTextField txtValor;
	private JDateChooser txtData;
	private JDateChooser textDataEn;
	private JTextField txtMecanico;
	private JTextField txtIdMec;
	private JLabel lblNewLabel_8;
	public String usuario;
	@SuppressWarnings("rawtypes")
	private JList listaMecanicos;
	private JScrollPane scrollPane_2;
	private JTextField txtTeste;
	private JButton btnOS;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Servico() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				txtTeste.setText(usuario);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servico.class.getResource("/img/bicicleta.png")));
		setTitle("Servicos");
		setBounds(100, 100, 572, 733);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane_2.setVisible(false);
				scrollPane.setVisible(false);
			}
		});
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("OS");
			lblNewLabel.setBounds(10, 18, 46, 14);
			contentPanel.add(lblNewLabel);
		}

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.setBounds(70, 15, 95, 20);
		contentPanel.add(txtOS);
		txtOS.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Modelo");
		lblNewLabel_1.setBounds(10, 309, 56, 14);
		contentPanel.add(lblNewLabel_1);

		txtBicicleta = new JTextField();
		txtBicicleta.setBounds(70, 306, 456, 20);
		contentPanel.add(txtBicicleta);
		txtBicicleta.setColumns(10);
		txtBicicleta.setDocument(new Validador(200));

		txtDefeito = new JTextField();
		txtDefeito.setBounds(10, 437, 516, 52);
		txtDefeito.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Defeito",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPanel.add(txtDefeito);
		txtDefeito.setColumns(10);
		txtDefeito.setDocument(new Validador(200));
		textDiagnostico = new JTextField();
		textDiagnostico.setBounds(10, 500, 516, 52);
		textDiagnostico
				.setBorder(new TitledBorder(null, "Diagnostico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		textDiagnostico.setColumns(10);
		contentPanel.add(textDiagnostico);
		textDiagnostico.setDocument(new Validador(200));
		cboStatus = new JComboBox();
		cboStatus.setBounds(70, 273, 172, 22);
		cboStatus.setModel(new DefaultComboBoxModel(new String[] { "", "Entregue", "Aguardando Peças", "Na oficina",
				"Aguardando Aprovação", "Orçamento Aprovado", "Em Analise", "Aguardando Mecanico" }));
		contentPanel.add(cboStatus);

		JLabel lblNewLabel_5 = new JLabel("Status");
		lblNewLabel_5.setBounds(10, 275, 46, 14);
		contentPanel.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Data");
		lblNewLabel_6.setBounds(268, 277, 46, 14);
		contentPanel.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Mecanico");
		lblNewLabel_7.setBounds(10, 340, 75, 14);
		contentPanel.add(lblNewLabel_7);

		textPeca = new JTextField();
		textPeca.setBounds(10, 368, 516, 58);
		textPeca.setBorder(
				new TitledBorder(null, "pe\u00E7as Utilizadas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(textPeca);
		textPeca.setColumns(10);
		textPeca.setDocument(new Validador(30));

		btnCreate = new JButton("");
		btnCreate.setBounds(20, 612, 64, 64);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarOs();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorder(null);
		btnCreate.setBackground(new Color(240, 240, 240));
		btnCreate.setIcon(new ImageIcon(Servico.class.getResource("/img/add_user.png")));
		btnCreate.setSelectedIcon(new ImageIcon(Servico.class.getResource("/img/add_user.png")));
		contentPanel.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.setBounds(94, 612, 64, 64);
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();
			}
		});
		btnUpdate.setIcon(new ImageIcon(
				Servico.class.getResource("/img/3592667_refresh_reload_repeat_rotate_rotate3_icon (1).png")));
		btnUpdate.setBorder(null);
		btnUpdate.setContentAreaFilled(false);
		contentPanel.add(btnUpdate);

		btnLimpar = new JButton("");
		btnLimpar.setBounds(175, 612, 64, 64);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Servico.class.getResource("/img/text_clear.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		contentPanel.add(btnLimpar);

		btnDelete = new JButton("");
		btnDelete.setBounds(261, 612, 64, 64);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnDelete.setIcon(new ImageIcon(Servico.class.getResource("/img/delete_icon.png")));
		btnDelete.setBorder(null);
		btnDelete.setContentAreaFilled(false);
		contentPanel.add(btnDelete);

		btnBuscar = new JButton("Buscar OS");
		btnBuscar.setBounds(175, 12, 95, 26);
		btnBuscar.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnBuscar.setBorderPainted(false);
		contentPanel.add(btnBuscar);

		getRootPane().setDefaultButton(btnBuscar);

		lblNewLabel_10 = new JLabel("Valor");
		lblNewLabel_10.setBounds(20, 563, 46, 14);
		contentPanel.add(lblNewLabel_10);

		JPanel panel = new JPanel();
		panel.setBounds(10, 43, 516, 104);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane_2.setVisible(false);
				scrollPane.setVisible(false);
			}
		});
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("ID Cliente");
		lblNewLabel_4.setBounds(350, 29, 63, 14);
		panel.add(lblNewLabel_4);

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCliente.setBounds(410, 26, 86, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("nome ");
		lblNewLabel_2.setBounds(10, 29, 46, 14);
		panel.add(lblNewLabel_2);

		txtNomeCli = new JTextField();
		txtNomeCli.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listaClientes();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtNomeCli.setBounds(72, 26, 268, 20);
		panel.add(txtNomeCli);
		txtNomeCli.setColumns(10);
		txtNomeCli.setDocument(new Validador(15));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 45, 268, 48);
		panel.add(scrollPane);

		listaClientes = new JList();
		scrollPane.setViewportView(listaClientes);
		listaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		scrollPane.setVisible(false);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setBounds(70, 563, 86, 20);
		txtValor.setDocument(new Validador(10));
		txtValor.setText("00");
		contentPanel.add(txtValor);
		txtValor.setColumns(10);
		getRootPane().setDefaultButton(btnCreate);
		txtData = new JDateChooser();
		txtData.setBounds(324, 275, 202, 20);
		txtData.setEnabled(false);
		contentPanel.add(txtData);
		JLabel lblNewLabel_3 = new JLabel("data saida");
		lblNewLabel_3.setBounds(192, 563, 75, 20);
		contentPanel.add(lblNewLabel_3);
		textDataEn = new JDateChooser();
		textDataEn.setBounds(277, 563, 144, 20);
		contentPanel.add(textDataEn);

		JPanel panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane_2.setVisible(false);
				scrollPane.setVisible(false);
			}
		});
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Mecanico",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 158, 516, 104);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_9 = new JLabel("Nome");
		lblNewLabel_9.setBounds(10, 24, 46, 14);
		panel_1.add(lblNewLabel_9);

		txtMecanico = new JTextField();
		txtMecanico.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listaMecanicos();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtMecanico.setBounds(68, 21, 276, 20);
		panel_1.add(txtMecanico);
		txtMecanico.setColumns(10);
		txtMecanico.setDocument(new Validador(30));

		txtIdMec = new JTextField();
		txtIdMec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtIdMec.setBounds(410, 21, 86, 20);
		panel_1.add(txtIdMec);
		txtIdMec.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("ID");
		lblNewLabel_11.setBounds(354, 24, 46, 14);
		panel_1.add(lblNewLabel_11);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setVisible(false);
		scrollPane_2.setBounds(68, 40, 276, 53);
		panel_1.add(scrollPane_2);

		listaMecanicos = new JList();
		listaMecanicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarMecanicoLista();

			}
		});
		scrollPane_2.setViewportView(listaMecanicos);

		lblNewLabel_8 = new JLabel("Usuario");
		lblNewLabel_8.setBounds(280, 18, 46, 14);
		contentPanel.add(lblNewLabel_8);

		txtTeste = new JTextField();
		txtTeste.setEditable(false);
		txtTeste.setBounds(334, 15, 192, 20);
		contentPanel.add(txtTeste);
		txtTeste.setColumns(10);

		btnOS = new JButton("");
		btnOS.setContentAreaFilled(false);
		btnOS.setBorder(null);
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setIcon(new ImageIcon(Servico.class.getResource("/img/print_icon.png")));
		btnOS.setToolTipText("Imprimir");
		btnOS.setBounds(335, 612, 64, 64);
		contentPanel.add(btnOS);
	}

	private void adicionarOs() {
		if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha Status da OS ");
			cboStatus.requestFocus();

		} else if (txtBicicleta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o modelo não pode estar vazio ");
			txtBicicleta.requestFocus();

		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o Defeito não pode estar vazio ");
			txtDefeito.requestFocus();

		} else if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o ID do cliente não pode estar vazio ");
			txtCliente.requestFocus();

		}

		else if (txtIdMec.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o ID do cliente não pode estar vazio ");
			txtCliente.requestFocus();
		} else {
			String create = "insert into servicos ( bicicleta,defeito,diagnostico,valor,statusOS,idcli,dataEntrega,mecanico,peçasUtil,idmecanico,usuario) values(?,?,?,?,?,?,?,?,?,?,?);";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtBicicleta.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, textDiagnostico.getText());
				pst.setString(4, txtValor.getText());
				pst.setString(5, cboStatus.getSelectedItem().toString());
				pst.setString(6, txtCliente.getText());
				pst.setString(8, txtMecanico.getText());
				pst.setString(9, textPeca.getText());
				pst.setString(10, txtIdMec.getText());
				pst.setString(11, txtTeste.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (textDataEn.getDate() == null) {
					pst.setString(7, null);
				} else {
					String dataFormatada = formatador.format(textDataEn.getDate());
					pst.setString(7, dataFormatada);
				}
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "OS  Adicionada com sucesso");
				recuperarOS();
				btnOS.setEnabled(true);
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "este Ordem de servico ja existe");
				txtOS.setText(null);
				txtOS.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void buscarOS() {
		String numOS = JOptionPane.showInputDialog("Numero da OS");
		String read = "select * from servicos where os = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, numOS);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtBicicleta.setText(rs.getString(2));
				txtDefeito.setText(rs.getString(3));
				textDiagnostico.setText(rs.getString(4));
				cboStatus.setSelectedItem(rs.getString(5));
				txtValor.setText(rs.getString(6));
				String setarData = rs.getString(7);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				txtData.setDate(dataFormatada);
				String setarData2 = rs.getString(8);
				if (setarData2 == null) {
					textDataEn.setDate(null);
				} else {
					Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
					textDataEn.setDate(dataFormatada2);
				}
				txtMecanico.setText(rs.getString(9));
				textPeca.setText(rs.getString(10));
				txtCliente.setText(rs.getString(11));
				txtIdMec.setText(rs.getString(12));
				txtTeste.setText(rs.getString(13));
				System.out.println(rs.getString(13));
				btnCreate.setEnabled(false);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnBuscar.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "OS inexistente");
				btnCreate.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void listaClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaClientes.setModel(modelo);
		String readLista = "select * from clientes where nome like '" + txtNomeCli.getText() + "%'" + "order by nome ";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNomeCli.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	private void buscarClienteLista() {
		int linha = listaClientes.getSelectedIndex();
		if (linha >= 0) {
			String read2 = "select * from clientes where nome like '" + txtNomeCli.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtNomeCli.setText(rs.getString(2));
					txtCliente.setText(rs.getString(1));
					btnBuscar.setEnabled(false);
				} else {
					scrollPane.setVisible(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void listaMecanicos() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaMecanicos.setModel(modelo);
		String readLista = "select * from mecanicos where nome like '" + txtMecanico.getText() + "%'"
				+ "order by nome ";
		try {

			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane_2.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtMecanico.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	private void buscarMecanicoLista() {
		int linha = listaMecanicos.getSelectedIndex();
		if (linha >= 0) {
			String read2 = "select * from mecanicos where nome like '" + txtMecanico.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtMecanico.setText(rs.getString(2));
					txtIdMec.setText(rs.getString(1));
					btnCreate.setEnabled(true);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					scrollPane_2.setVisible(false);
				} else {
					scrollPane.setVisible(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarOS() {
		if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o Numero de OS não pode estar vazio ");
			txtOS.requestFocus();
		} else if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha Status da OS ");
			cboStatus.requestFocus();

		} else if (txtBicicleta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o modelo não pode estar vazio ");
			txtBicicleta.requestFocus();

		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o Defeito não pode estar vazio ");
			txtDefeito.requestFocus();

		} else if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o ID do cliente não pode estar vazio ");
			txtCliente.requestFocus();

		} else if (txtIdMec.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o ID do cliente não pode estar vazio ");
			txtCliente.requestFocus();
		} else {
			String update = "update servicos set bicicleta=?,defeito=?,diagnostico=?,valor=?,statusOS=?,idcli=?,dataEntrega=?,mecanico=?,peçasUtil=?, idmecanico=? where os=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtBicicleta.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, textDiagnostico.getText());
				pst.setString(4, txtValor.getText());
				pst.setString(5, cboStatus.getSelectedItem().toString());
				pst.setString(6, txtCliente.getText());
				pst.setString(8, txtMecanico.getText());
				pst.setString(9, textPeca.getText());
				pst.setString(10, txtIdMec.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (textDataEn.getDate() == null) {
					pst.setString(7, null);
				} else {
					String dataFormatada = formatador.format(textDataEn.getDate());
					pst.setString(7, dataFormatada);
				}
				pst.setString(11, txtOS.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados da OS alterados com sucesso");
				con.close();
				limparCampos();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}

	private void excluirOS() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta OS", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from servicos where os=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtOS.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "OS deletado com sucesso");
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}

	}

	private void imprimirOS() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			document.open();
			String readOS = "select servicos.os, date_format(servicos.dataOS,'%d/%m/%Y') as data_entrada, servicos.usuario as usuário, servicos.bicicleta,servicos.defeito,servicos.statusOS as status_OS,servicos.diagnostico, mecanicos.nome as mecânico, servicos.valor, date_format(servicos.dataEntrega,'%d/%m/%Y') as data_saida, clientes.nome as cliente, clientes.cel, clientes.cep, clientes.email,clientes.endereco,clientes.num from servicos inner join clientes on servicos.idcli = clientes.idcli inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where os = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					document.add(new Paragraph(" "));
					Paragraph os = new Paragraph("Ordem de Serviço Nº " + rs.getString(1));
					os.setAlignment(Element.ALIGN_CENTER);
					document.add(os);
					document.add(new Paragraph(" "));
					Image image = Image.getInstance(Servico.class.getResource("/img/logo.png"));
					image.scaleToFit(360, 360);
					image.setAbsolutePosition(327, -60);
					document.add(image);
					Paragraph usuario = new Paragraph("OS Cadastrada por: " + rs.getString(3));
					usuario.setAlignment(Element.ALIGN_LEFT);
					document.add(usuario);
					document.add(new Paragraph(" "));
					Paragraph dadoscli = new Paragraph("Cliente: " + rs.getString(11) + " Telefone: " + rs.getString(12)
							+ " E-mail: " + rs.getString(14));
					dadoscli.setAlignment(Element.ALIGN_LEFT);
					document.add(dadoscli);
					Paragraph endereco = new Paragraph(
							"CEP: " + rs.getString(13) + " Endereco: " + rs.getString(15) + " Nº " + rs.getString(16));
					endereco.setAlignment(Element.ALIGN_LEFT);
					document.add(endereco);
					document.add(new Paragraph(" "));
					Paragraph paragraph2 = new Paragraph(
							"_____________________________Dados Do Atendimento_____________________________");
					paragraph2.setAlignment(Element.ALIGN_CENTER);
					document.add(paragraph2);
					document.add(new Paragraph(" "));
					Paragraph diagnostico2 = new Paragraph(
							"Status OS: " + rs.getString(6) + " Data de entrada: " + rs.getString(2));
					diagnostico2.setAlignment(Element.ALIGN_LEFT);
					document.add(diagnostico2);
					Paragraph defeito = new Paragraph("Bicicleta: " + rs.getString(4) + " defeito: " + rs.getString(5));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);
					Paragraph diagnostico = new Paragraph(
							"diagnostico: " + rs.getString(7) + " mecanico: " + rs.getString(8));
					diagnostico.setAlignment(Element.ALIGN_LEFT);
					document.add(diagnostico);
					document.add(new Paragraph(" "));
					Paragraph paragraph3 = new Paragraph(
							"_________________________________Dados de saida _______________________________");
					paragraph3.setAlignment(Element.ALIGN_CENTER);
					document.add(paragraph3);
					document.add(new Paragraph(" "));
					if (rs.getString(10) == null) {
						Paragraph valores = new Paragraph("Valor: " + rs.getString(9) + " Aguardando aprovação ");
						valores.setAlignment(Element.ALIGN_LEFT);
						document.add(valores);
					} else {
						Paragraph valores = new Paragraph(
								"Valor: " + rs.getString(9) + " Data de saida: " + rs.getString(10));
						valores.setAlignment(Element.ALIGN_LEFT);
						document.add(valores);
					}
				}
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	private void limparCampos() {
		txtOS.setText(null);
		txtBicicleta.setText(null);
		txtDefeito.setText(null);
		txtCliente.setText(null);
		txtData.setDate(null);
		txtMecanico.setText(null);
		textDataEn.setDate(null);
		textPeca.setText(null);
		txtDefeito.setText(null);
		textDiagnostico.setText(null);
		txtValor.setText("00");
		txtNomeCli.setText(null);
		txtIdMec.setText(null);
		scrollPane.setVisible(false);
		cboStatus.setSelectedItem(null);
		btnBuscar.setEnabled(true);
		btnCreate.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
	}

	private void recuperarOS() {
		String reados = "select max(os) from servicos";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(reados);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));

			} else {
				JOptionPane.showMessageDialog(null, "os Inexistente");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println();
		}

	}
}