package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import util.Validador;

import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("unused")
public class Usuarios extends JDialog {

	/**
		 * 
		 */
	private static final long serialVersionUID = 2803359885001642107L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;

	private JTextField txtID;
	private JTextField textNome;
	private JTextField textLogin;
	private JPasswordField textPassword;
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JComboBox<Object> cboPerfil;
	private JCheckBox chkSenha;
	@SuppressWarnings("rawtypes")
	private JList listaUsuarios;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/usuarios.png")));
		setResizable(false);
		setModal(true);
		setTitle("Usuarios");
		setBounds(100, 100, 472, 332);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(71, 102, 218, 101);
		getContentPane().add(scrollPane);

		listaUsuarios = new JList();
		scrollPane.setViewportView(listaUsuarios);
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				buscarUsuarioLista();
			}
		});
		listaUsuarios.setBorder(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(17, 14, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(17, 84, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(17, 56, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setBounds(17, 118, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtID = new JTextField();
		txtID.setToolTipText("Identificador");
		txtID.setEditable(false);
		txtID.setBounds(73, 11, 89, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		textNome = new JTextField();
		textNome.setBorder(null);
		textNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		textNome.setToolTipText("Nome");
		textNome.setBounds(73, 81, 216, 20);
		getContentPane().add(textNome);
		textNome.setColumns(10);
		textNome.setDocument(new Validador(30));

		textLogin = new JTextField();
		textLogin.setBounds(73, 53, 216, 20);
		getContentPane().add(textLogin);
		textLogin.setColumns(10);
		textLogin.setDocument(new Validador(30));
		btnCreate = new JButton("");
		btnCreate.setContentAreaFilled(false);
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarContato();
			}
		});
		btnCreate.setBorderPainted(false);
		btnCreate.setBorder(null);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add_user.png")));
		btnCreate.setBounds(55, 201, 64, 64);
		getContentPane().add(btnCreate);

		JButton btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/text_clear.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(205, 201, 64, 64);
		getContentPane().add(btnLimpar);

		textPassword = new JPasswordField();
		textPassword.setBounds(73, 115, 216, 20);
		getContentPane().add(textPassword);

		btnBuscar = new JButton("");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarContato();
			}
		});
		btnBuscar.setBorderPainted(false);
		btnBuscar.setBorder(null);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/buscar.png")));
		btnBuscar.setBounds(293, 64, 48, 48);
		getContentPane().add(btnBuscar);
		getRootPane().setDefaultButton(btnBuscar);
		btnUpdate = new JButton("");
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkSenha.isSelected()) {
					editarUsuarioSenha();

				} else {
					editarUsuario();
				}
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setBorder(null);
		btnUpdate.setIcon(new ImageIcon(
				Usuarios.class.getResource("/img/3592667_refresh_reload_repeat_rotate_rotate3_icon (1).png")));
		btnUpdate.setBounds(131, 201, 64, 64);
		getContentPane().add(btnUpdate);

		btnDelete = new JButton("");
		btnDelete.setContentAreaFilled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete_icon.png")));
		btnDelete.setBorder(null);
		btnDelete.setBounds(279, 201, 64, 64);
		getContentPane().add(btnDelete);

		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setBounds(17, 156, 46, 14);
		getContentPane().add(lblNewLabel_4);

		cboPerfil = new JComboBox<Object>();
		cboPerfil.setModel(new DefaultComboBoxModel<Object>(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(73, 152, 216, 22);
		getContentPane().add(cboPerfil);

		chkSenha = new JCheckBox("Alterar a senha");
		chkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPassword.setEditable(true);
				textPassword.setText(null);
				textPassword.requestFocus();
				textPassword.setBackground(Color.yellow);
			}
		});
		chkSenha.setVisible(false);
		chkSenha.setBounds(295, 114, 131, 23);
		getContentPane().add(chkSenha);

	}

	/**
	 * Método para adicionar um contato no banco
	 */
	private void adicionarContato() {
		String capturaSenha = new String(textPassword.getPassword());
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			textNome.requestFocus();
		} else if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");
			textLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha ");
			textLogin.requestFocus();

		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do usuario ");
			textLogin.requestFocus();

		} else {
			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, textNome.getText());
				pst.setString(2, textLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuario  adicionado com sucesso");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "este Login ja esta sendo utilizado");
				textLogin.setText(null);
				textLogin.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}

		}

	}

	private void buscarContato() {

		if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuario");
			textLogin.requestFocus();
		} else {
			String read = "select * from usuarios where login = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, textLogin.getText());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtID.setText(rs.getString(1));
					textNome.setText(rs.getString(2));
					textLogin.setText(rs.getString(3));
					textPassword.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					chkSenha.setVisible(true);
					btnCreate.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnBuscar.setEnabled(false);
					textPassword.setEditable(false);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario inexistente");
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
	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios.setModel(modelo);
		String readLista = "select * from usuarios where nome like '" + textNome.getText() + "%'" + "order by nome ";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (textNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	private void buscarUsuarioLista() {

		int linha = listaUsuarios.getSelectedIndex();

		if (linha >= 0) {
			System.out.println(linha);
			String read2 = "select * from usuarios where nome like '" + textNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					textNome.setText(rs.getString(2));
					textLogin.setText(rs.getString(3));
					textPassword.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					chkSenha.setVisible(true);
					textPassword.setEditable(false);

					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
				} else {
					System.out.println("usuário não cadastrado");
					scrollPane.setVisible(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuarioSenha() {
		String capturaSenha = new String(textPassword.getPassword());
		if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			textLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha o senha ");
			textPassword.requestFocus();

		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do usuario ");
			textPassword.requestFocus();

		} else {

			String update = "update usuarios set nome=?,login=?,senha=md5(?),perfil=? where iduser=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, textNome.getText());
				pst.setString(2, textLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, (String) cboPerfil.getSelectedItem());
				pst.setString(5, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do usuario alterados com sucesso");
				con.close();
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Este Login ja esta sendo utilizado");
				textLogin.setText(null);
				textLogin.requestFocus();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}

	private void editarUsuario() {
		if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			textLogin.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do usuario ");
			textPassword.requestFocus();

		} else {
			String update = "update usuarios set nome=?,login=?,perfil=? where iduser=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, textNome.getText());
				pst.setString(2, textLogin.getText());
				pst.setString(3, (String) cboPerfil.getSelectedItem());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do usuario alterados com sucesso");
				con.close();
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Este Login ja esta sendo utilizado");
				textLogin.setText(null);
				textLogin.requestFocus();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}

	private void excluirContato() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuario", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where iduser=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso");

				con.close();

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}

	}

	private void limparCampos() {
		txtID.setText(null);
		textNome.setText(null);
		textLogin.setText(null);
		textPassword.setText(null);
		textPassword.setEnabled(true);
		textPassword.setEditable(true);
		scrollPane.setVisible(false);
		cboPerfil.setSelectedItem(null);
		btnBuscar.setEnabled(true);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		chkSenha.setVisible(false);
		textPassword.setBackground(Color.white);

	}
}
