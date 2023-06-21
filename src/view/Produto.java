package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import com.toedter.calendar.JDateChooser;

import model.DAO;
import util.Validador;

@SuppressWarnings("serial")
public class Produto<txtNomeCli> extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtProd;
	private JTextField txtDescrição;
	private JTextField txtIdFor;

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		try {
			Produto dialog = new Produto();
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
	private JComboBox cboUnidade;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnLimpar;
	private JButton btnDelete;
	private JButton btnBuscar;
	private JTextField txtNomefor;
	@SuppressWarnings("rawtypes")
	private JList listaClientes;
	private JScrollPane scrollPane;
	private JTextField txtValor;
	private JDateChooser txtDataEn;
	private JDateChooser txtDataVal;
	public String usuario;
	private JTextField txtQuant;
	private JTextField txtQuantMin;
	private JTextField txtBarCode;
	private JTextField txtLocal;
	private JTextField txtNomeProd;
	private JScrollPane scrollPane_1;
	@SuppressWarnings("rawtypes")
	private JList listaProduto;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Produto() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produto.class.getResource("/img/bicicleta.png")));
		setTitle("Produtos");
		setBounds(100, 100, 529, 477);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVisible(false);
		scrollPane_1.setBounds(10, 195, 401, 63);
		contentPanel.add(scrollPane_1);

		listaProduto = new JList();
		scrollPane_1.setViewportView(listaProduto);
		listaProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				buscarProdLista();
			}
		});
		{
			JLabel lblNewLabel = new JLabel("IDProd");
			lblNewLabel.setBounds(10, 18, 46, 14);
			contentPanel.add(lblNewLabel);
		}

		txtProd = new JTextField();
		txtProd.setBounds(70, 15, 95, 20);
		txtProd.setEditable(false);
		contentPanel.add(txtProd);
		txtProd.setColumns(10);

		txtDescrição = new JTextField();
		txtDescrição.setBounds(10, 208, 401, 36);
		txtDescrição.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Descri\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPanel.add(txtDescrição);
		txtDescrição.setColumns(10);
		txtDescrição.setDocument(new Validador(150));
		cboUnidade = new JComboBox();
		cboUnidade.setBounds(347, 255, 64, 36);
		cboUnidade.setModel(new DefaultComboBoxModel(new String[] { "", "kg", "g", "un", "PC", "CX" }));
		contentPanel.add(cboUnidade);

		JLabel lblNewLabel_6 = new JLabel("Data");
		lblNewLabel_6.setBounds(285, 14, 46, 14);
		contentPanel.add(lblNewLabel_6);

		btnCreate = new JButton("");
		btnCreate.setBounds(10, 363, 64, 64);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarProd();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorder(null);
		btnCreate.setBackground(new Color(240, 240, 240));
		btnCreate.setIcon(new ImageIcon(Produto.class.getResource("/img/add_user.png")));
		btnCreate.setSelectedIcon(new ImageIcon(Produto.class.getResource("/img/add_user.png")));
		contentPanel.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.setBounds(84, 363, 64, 64);
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();
			}
		});
		btnUpdate.setIcon(new ImageIcon(
				Produto.class.getResource("/img/3592667_refresh_reload_repeat_rotate_rotate3_icon (1).png")));
		btnUpdate.setBorder(null);
		btnUpdate.setContentAreaFilled(false);
		contentPanel.add(btnUpdate);

		btnLimpar = new JButton("");
		btnLimpar.setBounds(158, 363, 64, 64);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Produto.class.getResource("/img/text_clear.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		contentPanel.add(btnLimpar);

		btnDelete = new JButton("");
		btnDelete.setBounds(232, 363, 64, 64);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnDelete.setIcon(new ImageIcon(Produto.class.getResource("/img/delete_icon.png")));
		btnDelete.setBorder(null);
		btnDelete.setContentAreaFilled(false);
		contentPanel.add(btnDelete);

		btnBuscar = new JButton("Buscar Prod");
		btnBuscar.setBounds(175, 12, 95, 26);
		btnBuscar.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarProd();
			}
		});
		btnBuscar.setBorderPainted(false);
		contentPanel.add(btnBuscar);
		JPanel panel = new JPanel();
		panel.setBounds(10, 43, 401, 104);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				scrollPane.setVisible(false);
			}
		});
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("ID for");
		lblNewLabel_4.setBounds(264, 32, 50, 14);
		panel.add(lblNewLabel_4);

		txtIdFor = new JTextField();
		txtIdFor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtIdFor.setBounds(300, 29, 86, 20);
		panel.add(txtIdFor);
		txtIdFor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("fornecedor");
		lblNewLabel_2.setBounds(10, 29, 63, 14);
		panel.add(lblNewLabel_2);

		txtNomefor = new JTextField();
		txtNomefor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listaFornecedores();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtNomefor.setBounds(72, 26, 182, 20);
		panel.add(txtNomefor);
		txtNomefor.setColumns(10);
		txtNomefor.setDocument(new Validador(30));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 45, 182, 48);
		panel.add(scrollPane);

		listaClientes = new JList();
		scrollPane.setViewportView(listaClientes);
		listaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarforLista();
			}
		});
		scrollPane.setVisible(false);

		txtValor = new JTextField();
		txtValor.setBorder(new TitledBorder(null, "Valor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtValor.setText("0");
		txtValor.setBounds(271, 314, 86, 38);
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setDocument(new Validador(10));
		contentPanel.add(txtValor);
		txtValor.setColumns(10);
		txtDataEn = new JDateChooser();
		txtDataEn.setBounds(341, 12, 144, 20);
		txtDataEn.setEnabled(false);
		contentPanel.add(txtDataEn);

		txtDataVal = new JDateChooser();
		txtDataVal.setBorder(new TitledBorder(null, "validade", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtDataVal.setBounds(10, 310, 144, 42);
		contentPanel.add(txtDataVal);
		txtQuant = new JTextField();
		txtQuant.setBorder(new TitledBorder(null, "Quant.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtQuant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtQuant.setBounds(273, 255, 64, 38);
		contentPanel.add(txtQuant);
		txtQuant.setColumns(10);

		txtQuantMin = new JTextField();
		txtQuantMin.setBorder(new TitledBorder(null, "Quant.min", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtQuantMin.setText("00");
		txtQuantMin.setDocument(new Validador(10));
		txtQuantMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtQuantMin.setBounds(175, 314, 86, 38);
		txtQuantMin.setColumns(10);
		contentPanel.add(txtQuantMin);

		txtBarCode = new JTextField();
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarBarcode();
				}
			}
		});
		txtBarCode.setBorder(new TitledBorder(null, "Cod.bar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtBarCode.setBounds(10, 255, 138, 38);
		contentPanel.add(txtBarCode);
		txtBarCode.setColumns(10);

		txtLocal = new JTextField();
		txtLocal.setBorder(new TitledBorder(null, "Local", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtLocal.setBounds(175, 255, 95, 36);
		txtLocal.setDocument(new Validador(10));
		contentPanel.add(txtLocal);
		txtLocal.setColumns(10);

		txtNomeProd = new JTextField();
		txtNomeProd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listaProdutos();
			}
		});
		txtNomeProd.setBorder(
				new TitledBorder(null, "Nome do produto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtNomeProd.setBounds(10, 155, 401, 42);
		contentPanel.add(txtNomeProd);
		txtNomeProd.setColumns(10);
	}

	private void adicionarProd() {

		if (cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade  de medida");
			cboUnidade.requestFocus();

		} else if (txtDescrição.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o Defeito não pode estar vazio ");
			txtDescrição.requestFocus();

		} else if (txtIdFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o ID do Fornecedor não pode estar vazio ");
			txtIdFor.requestFocus();

		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o local de armazenamento não pode estar vazio");
			txtLocal.requestFocus();

		} else if (txtQuant.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "a quantidade não pode estar vazio vazio");
			txtQuant.requestFocus();

		} else if (txtQuantMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "a quantidade minima não pode estar vazio");
			txtQuantMin.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "O valor não pode estar vazio");
			txtValor.requestFocus();

		} else {
			String create = "insert into produtos (barcode,descricao,estoque,estoquemin,valor,unidade_medida,localarma,validade,idfor,nomeproduto) value (?,?,?,?,?,?,?,?,?,?);";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtDescrição.getText());
				pst.setString(3, txtQuant.getText());
				pst.setString(4, txtQuantMin.getText());
				pst.setString(5, txtValor.getText());
				pst.setString(6, cboUnidade.getSelectedItem().toString());
				pst.setString(7, txtLocal.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (txtDataVal.getDate() == null) {
					pst.setString(8, null);
				} else {
					String dataFormatada = formatador.format(txtDataVal.getDate());
					pst.setString(8, dataFormatada);
				}
				pst.setString(9, txtIdFor.getText());
				pst.setString(10, txtNomeProd.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Produto  Adicionado com sucesso");
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "este Ordem de servico ja existe");
				txtProd.setText(null);
				txtProd.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void buscarProd() {
		String numOS = JOptionPane.showInputDialog("Numero da Produto");
		String read = "select * from produtos where idprod = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, numOS);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtProd.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtDescrição.setText(rs.getString(3));
				txtQuant.setText(rs.getString(4));
				txtQuantMin.setText(rs.getString(5));
				txtValor.setText(rs.getString(6));
				cboUnidade.setSelectedItem(rs.getString(7));
				String setarData = rs.getString(11);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				txtDataEn.setDate(dataFormatada);
				txtLocal.setText(rs.getString(8));
				String setarData2 = rs.getString(9);
				if (setarData2 == null) {
					txtDataVal.setDate(null);
				} else {
					Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
					txtDataVal.setDate(dataFormatada2);
				}
				txtIdFor.setText(rs.getString(10));
				txtNomeProd.setText(rs.getString(12));
				btnCreate.setEnabled(false);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnBuscar.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "Produto inexistente");
				btnCreate.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void listaProdutos() {
		@SuppressWarnings("rawtypes")
		DefaultListModel modelo = new DefaultListModel<>();
		listaProduto.setModel(modelo);
		String readLista = "select * from produtos where nomeproduto like '" + txtNomeProd.getText() + "%'"
				+ "order by nomeproduto ";
		try {

			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane_1.setVisible(true);
				modelo.addElement(rs.getString(12));
				if (txtNomeProd.getText().isEmpty()) {
					scrollPane_1.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	private void buscarProdLista() {
		int linha = listaProduto.getSelectedIndex();
		if (linha >= 0) {
			String read2 = "select * from produtos where nomeproduto like '" + txtNomeProd.getText() + "%'"
					+ "order by nomeproduto limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtProd.setText(rs.getString(1));
					txtBarCode.setText(rs.getString(2));
					txtDescrição.setText(rs.getString(3));
					txtQuant.setText(rs.getString(4));
					txtQuantMin.setText(rs.getString(5));
					txtValor.setText(rs.getString(6));
					cboUnidade.setSelectedItem(rs.getString(7));
					String setarData = rs.getString(11);
					Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
					txtDataEn.setDate(dataFormatada);
					txtLocal.setText(rs.getString(8));
					String setarData2 = rs.getString(9);
					if (setarData2 == null) {
						txtDataVal.setDate(null);
					} else {
						Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
						txtDataVal.setDate(dataFormatada2);
					}
					txtIdFor.setText(rs.getString(10));
					txtNomeProd.setText(rs.getString(12));
					btnCreate.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnBuscar.setEnabled(true);
					scrollPane_1.setVisible(false);
				} else {
					scrollPane_1.setVisible(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void listaFornecedores() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaClientes.setModel(modelo);
		String readLista = "select * from Fornecedores where razaosocial like '" + txtNomefor.getText() + "%'"
				+ "order by razaosocial ";
		try {

			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNomefor.getText().isEmpty()) {
					scrollPane.setVisible(false);

				}
			}
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	private void buscarforLista() {
		int linha = listaClientes.getSelectedIndex();
		if (linha >= 0) {
			String read2 = "select * from Fornecedores where razaosocial like '" + txtNomefor.getText() + "%'"
					+ "order by razaosocial limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtNomefor.setText(rs.getString(2));
					txtIdFor.setText(rs.getString(1));
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

	private void editarOS() {
		if (cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade  de medida");
			cboUnidade.requestFocus();

		} else if (txtDescrição.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o Defeito não pode estar vazio ");
			txtDescrição.requestFocus();

		} else if (txtIdFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o ID do Fornecedor não pode estar vazio ");
			txtIdFor.requestFocus();

		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o local do Fornecedor não pode estar vazio ");
			txtLocal.requestFocus();

		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "o local de armazenamento não pode estar vazio");
			txtLocal.requestFocus();

		} else if (txtQuant.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "a quantidade não pode estar vazio vazio");
			txtQuant.requestFocus();

		} else if (txtQuantMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "a quantidade minima não pode estar vazio");
			txtQuantMin.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "O valor não pode estar vazio");
			txtValor.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "O valor não pode estar vazio");
			txtValor.requestFocus();
		} else {

			String update = "update produtos set barcode=?,descricao=?,estoque=?,estoquemin=?,valor=?,unidade_medida=?,localarma=?,idfor=?,validade=?,nomeproduto=? where idProd=?;";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtDescrição.getText());
				pst.setString(3, txtQuant.getText());
				pst.setString(4, txtQuantMin.getText());
				pst.setString(5, txtValor.getText());
				pst.setString(6, cboUnidade.getSelectedItem().toString());
				pst.setString(7, txtLocal.getText());
				pst.setString(8, txtIdFor.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (txtDataVal.getDate() == null) {
					pst.setString(9, null);
				} else {
					String dataFormatada = formatador.format(txtDataVal.getDate());
					pst.setString(9, dataFormatada);
				}
				pst.setString(10, txtNomeProd.getText());
				pst.setString(11, txtProd.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados da Produto alterados com sucesso");
				con.close();
				limparCampos();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}

	private void excluirOS() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta Produto", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from produtos where idprod=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtProd.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Produto deletado com sucesso");
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}

	}

	private void buscarBarcode() {
		String read = "select * from produtos where barcode = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtBarCode.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtProd.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtDescrição.setText(rs.getString(3));
				txtQuant.setText(rs.getString(4));
				txtQuantMin.setText(rs.getString(5));
				txtValor.setText(rs.getString(6));
				cboUnidade.setSelectedItem(rs.getString(7));

				String setarData = rs.getString(11);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);

				txtDataEn.setDate(dataFormatada);
				txtLocal.setText(rs.getString(8));
				String setarData2 = rs.getString(9);
				if (setarData2 == null) {
					txtDataVal.setDate(null);
				} else {
					Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
					txtDataVal.setDate(dataFormatada2);
				}
				txtIdFor.setText(rs.getString(10));
				txtNomeProd.setText(rs.getString(12));
				btnCreate.setEnabled(false);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnBuscar.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "Produto inexistente");
				btnCreate.setEnabled(true);
				btnBuscar.setEnabled(false);
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void limparCampos() {

		String zero = null;
		txtProd.setText(null);
		txtQuantMin.setText(zero);
		txtQuant.setText(zero);
		txtValor.setText(zero);
		txtLocal.setText(null);
		txtBarCode.setText(null);
		txtDescrição.setText(null);
		txtIdFor.setText(null);
		txtDataEn.setDate(null);
		txtDataVal.setDate(null);
		txtDescrição.setText(null);
		txtNomefor.setText(null);
		txtNomeProd.setText(null);
		scrollPane.setVisible(false);
		cboUnidade.setSelectedItem(null);
		btnBuscar.setEnabled(true);
		btnCreate.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);

	}
}
