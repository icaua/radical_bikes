package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mecanicos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNick;
	private JTextField txtNome;
	private JTextField txtTel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			mecanicos dialog = new mecanicos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnBuscar;
	@SuppressWarnings("rawtypes")
	private JList listaMecanicos;
	private JScrollPane scrollPane;

	@SuppressWarnings("rawtypes")
	public mecanicos() {
		setTitle("Mecanicos");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(mecanicos.class.getResource("/img/bicicleta.png")));
		setBounds(100, 100, 426, 272);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(66, 91, 275, 77);
		contentPanel.add(scrollPane);

		listaMecanicos = new JList();
		listaMecanicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarMecanicoLista();
			}
		});
		scrollPane.setViewportView(listaMecanicos);
		{
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(10, 20, 46, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Nome");
			lblNewLabel_1.setBounds(10, 76, 46, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Apelido");
			lblNewLabel_2.setBounds(10, 48, 46, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Telefone");
			lblNewLabel_3.setBounds(10, 104, 46, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setBounds(66, 17, 86, 20);
			contentPanel.add(txtID);
			txtID.setColumns(10);
		}
		{
			txtNick = new JTextField();
			txtNick.setBounds(66, 45, 275, 20);
			contentPanel.add(txtNick);
			txtNick.setColumns(10);
		}
		{
			txtNome = new JTextField();
			txtNome.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					listaMecanicos();
				}
			});
			txtNome.setBounds(66, 73, 275, 20);
			contentPanel.add(txtNome);
			txtNome.setColumns(10);
		}
		{
			txtTel = new JTextField();
			txtTel.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0987654321.";
					if (!caracteres.contains(e.getKeyChar() + "")) {
						e.consume();
					}
				}
			});
			txtTel.setBounds(66, 101, 275, 20);
			contentPanel.add(txtTel);
			txtTel.setColumns(10);
		}
		{
			btnBuscar = new JButton("");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarMecanico();
				}
			});
			btnBuscar.setContentAreaFilled(false);
			btnBuscar.setBorder(null);
			btnBuscar.setBorderPainted(false);
			btnBuscar.setIcon(new ImageIcon(mecanicos.class.getResource("/img/352091_search_icon.png")));
			btnBuscar.setBounds(352, 31, 48, 48);
			contentPanel.add(btnBuscar);
		}
		{
			btnCreate = new JButton("");
			btnCreate.setEnabled(false);
			btnCreate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adicionarMecanico();
				}
			});
			btnCreate.setBorder(null);
			btnCreate.setBorderPainted(false);
			btnCreate.setContentAreaFilled(false);
			btnCreate.setIcon(new ImageIcon(mecanicos.class.getResource("/img/add_user.png")));
			btnCreate.setBounds(10, 157, 64, 64);
			contentPanel.add(btnCreate);
		}
		{
			btnUpdate = new JButton("");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editarMecanico();
				}
			});
			btnUpdate.setEnabled(false);
			btnUpdate.setContentAreaFilled(false);
			btnUpdate.setBorder(null);
			btnUpdate.setIcon(new ImageIcon(
					mecanicos.class.getResource("/img/3592667_refresh_reload_repeat_rotate_rotate3_icon (1).png")));
			btnUpdate.setBounds(84, 157, 64, 64);
			contentPanel.add(btnUpdate);
		}
		{
			JButton btnLimpar = new JButton("");
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limparCampos();
				}
			});
			btnLimpar.setBorder(null);
			btnLimpar.setContentAreaFilled(false);
			btnLimpar.setIcon(new ImageIcon(mecanicos.class.getResource("/img/text_clear.png")));
			btnLimpar.setBounds(158, 157, 64, 64);
			contentPanel.add(btnLimpar);
		}
		{
			btnDelete = new JButton("");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					excluirContato();
				}
			});
			btnDelete.setEnabled(false);
			btnDelete.setBorder(null);
			btnDelete.setContentAreaFilled(false);
			btnDelete.setBorderPainted(false);
			btnDelete.setIcon(new ImageIcon(mecanicos.class.getResource("/img/delete_icon.png")));
			btnDelete.setBounds(232, 157, 64, 64);
			contentPanel.add(btnDelete);
		}

	}

	private void adicionarMecanico() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNome.requestFocus();
		} else if (txtNick.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF do Cliente ");
			txtNick.requestFocus();

		} else {
			String create = "insert into mecanicos (nome,nick,fone) values (?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtNick.getText());
				pst.setString(3, txtTel.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Mecanico  adicionado com sucesso");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Este apelido ja esta sendo utilizado");
				txtNick.setText(null);
				txtNick.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}

		}

	}

	private void buscarMecanico() {

		if (txtNick.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Cliente");
			txtNick.requestFocus();
		} else {

			String read = "select * from mecanicos where nick = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtNick.getText());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtID.setText(rs.getString(1));
					txtNick.setText(rs.getString(3));
					txtNome.setText(rs.getString(2));
					txtTel.setText(rs.getString(4));
					btnCreate.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnBuscar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Mecanico inexistente");
					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);
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
		String readLista = "select * from mecanicos where nome like '" + txtNome.getText() + "%'" + "order by nome ";
		try {

			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
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
			String read2 = "select * from mecanicos where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtID.setText(rs.getString(1));
					txtNick.setText(rs.getString(3));
					txtNome.setText(rs.getString(2));
					txtTel.setText(rs.getString(4));
					btnCreate.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnBuscar.setEnabled(false);
					scrollPane.setVisible(false);
				} else {
					scrollPane.setVisible(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarMecanico() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNome.requestFocus();
		} else if (txtNick.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF do Cliente ");
			txtNick.requestFocus();

		} else {
			String update = "update mecanicos set nome=?,nick=?,fone=? where idmecanico=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtNick.getText());
				pst.setString(3, txtTel.getText());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Cliente alterados com sucesso");
				con.close();
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Este Apelido ja esta sendo utilizado");
				txtNick.setText(null);
				txtNick.requestFocus();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}

	private void excluirContato() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste mecanico", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from mecanicos where idmecanico=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "mecanico deletado com sucesso");

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "mecanico Possui OS pendente");

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}

	}

	private void limparCampos() {

		txtID.setText(null);
		txtNick.setText(null);
		txtNome.setText(null);
		txtTel.setText(null);
		btnBuscar.setEnabled(true);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		scrollPane.setVisible(false);
	}
}
