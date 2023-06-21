package view;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

public class Relatorios extends JDialog {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
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
	public Relatorios() {
		setResizable(false);
		setBounds(100, 100, 570, 346);
		getContentPane().setLayout(null);

		JButton btnRelClientes = new JButton("Clientes");
		btnRelClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatoriosClientes();
			}
		});
		btnRelClientes.setBounds(10, 26, 225, 31);
		getContentPane().add(btnRelClientes);

		JButton btnAguardandoMecanico = new JButton("aguardando Mecanico");
		btnAguardandoMecanico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatoriosAguardandoMecanico();
			}
		});
		btnAguardandoMecanico.setBounds(10, 68, 225, 31);
		getContentPane().add(btnAguardandoMecanico);

		JButton btnAguardandoAprovação = new JButton("Aguardando Aprovação");
		btnAguardandoAprovação.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AguardandoAprovação();
			}
		});
		btnAguardandoAprovação.setBounds(245, 68, 299, 31);
		getContentPane().add(btnAguardandoAprovação);

		JButton btnAguardandoPeças = new JButton("aguardando Peças");
		btnAguardandoPeças.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aguardandoPeças();
			}
		});
		btnAguardandoPeças.setBounds(245, 26, 299, 31);
		getContentPane().add(btnAguardandoPeças);

		JButton btnValidade = new JButton("estoque minimo");
		btnValidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				estoqueMin();
			}
		});
		btnValidade.setBounds(10, 110, 225, 31);
		getContentPane().add(btnValidade);

	}

	private void relatoriosClientes() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));
			String readClientes = "select nome,cel,email from clientes order by nome";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				ResultSet rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(3);
				PdfPCell col1 = new PdfPCell(new Paragraph("cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("cel"));
				PdfPCell col3 = new PdfPCell(new Paragraph("email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}
				document.add(tabela);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();

		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

	}

	private void relatoriosAguardandoMecanico() {

		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("aguardandoMecanicos.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Aguardando Mecanicos :"));
			document.add(new Paragraph(" "));

			String readAguardandoMecanico = "select os,bicicleta,defeito,date_format(dataOS,'%d/%m/%y ')as dataOS from servicos where statusOS = \"Aguardando Mecanico\"";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readAguardandoMecanico);
				ResultSet rs = pst.executeQuery();
				PdfPTable tabela2 = new PdfPTable(4);
				PdfPCell col1 = new PdfPCell(new Paragraph("os"));
				PdfPCell col2 = new PdfPCell(new Paragraph("bicicleta"));
				PdfPCell col3 = new PdfPCell(new Paragraph("defeito"));
				PdfPCell col4 = new PdfPCell(new Paragraph("dataOS"));

				tabela2.addCell(col1);
				tabela2.addCell(col2);
				tabela2.addCell(col3);
				tabela2.addCell(col4);
				while (rs.next()) {
					tabela2.addCell(rs.getString(1));
					tabela2.addCell(rs.getString(2));
					tabela2.addCell(rs.getString(3));
					tabela2.addCell(rs.getString(4));
				}
				document.add(tabela2);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("aguardandoMecanicos.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

	}

	private void AguardandoAprovação() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("aguardandoAprovação.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Aguardando aprovação :"));
			document.add(new Paragraph(" "));
			String readAguardandoAprovacao = "select os,bicicleta,defeito,date_format(dataOS,'%d/%m/%y')as dataOS,valor, clientes.nome as nome_cliente,clientes.cel as telefone, clientes.email as email from servicos inner join clientes on servicos.idcli = clientes.idcli where statusOS = \"Aguardando Aprovação\"";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(readAguardandoAprovacao);
				ResultSet rs = pst.executeQuery();
				PdfPTable tabela2 = new PdfPTable(8);
				PdfPCell col1 = new PdfPCell(new Paragraph("os"));
				PdfPCell col2 = new PdfPCell(new Paragraph("bicicleta"));
				PdfPCell col3 = new PdfPCell(new Paragraph("defeito"));
				PdfPCell col4 = new PdfPCell(new Paragraph("dataOS"));
				PdfPCell col5 = new PdfPCell(new Paragraph("valor"));
				PdfPCell col6 = new PdfPCell(new Paragraph("nome_cliente"));
				PdfPCell col7 = new PdfPCell(new Paragraph("telefone"));
				PdfPCell col8 = new PdfPCell(new Paragraph("e-mail"));
				tabela2.addCell(col1);
				tabela2.addCell(col2);
				tabela2.addCell(col3);
				tabela2.addCell(col4);
				tabela2.addCell(col5);
				tabela2.addCell(col6);
				tabela2.addCell(col7);
				tabela2.addCell(col8);
				while (rs.next()) {
					tabela2.addCell(rs.getString(1));
					tabela2.addCell(rs.getString(2));
					tabela2.addCell(rs.getString(3));
					tabela2.addCell(rs.getString(4));
					tabela2.addCell(rs.getString(5));
					tabela2.addCell(rs.getString(6));
					tabela2.addCell(rs.getString(7));
					tabela2.addCell(rs.getString(8));

				}
				document.add(tabela2);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("aguardandoAprovação.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

	}

	private void aguardandoPeças() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("aguardandoPeças.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Aguardando aprovação :"));
			document.add(new Paragraph(" "));
			String readAguardandoAprovacao = "select os,bicicleta,defeito,date_format(dataOS,'%d/%m/%y')as dataOS, mecanicos.nome as nome_mecanico,peçasUtil  from servicos inner join mecanicos on servicos.idmecanico = mecanicos.idmecanico where statusOS = \"Aguardando Peças\"";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readAguardandoAprovacao);
				ResultSet rs = pst.executeQuery();
				PdfPTable tabela2 = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("os"));
				PdfPCell col2 = new PdfPCell(new Paragraph("bicicleta"));
				PdfPCell col3 = new PdfPCell(new Paragraph("defeito"));
				PdfPCell col4 = new PdfPCell(new Paragraph("dataOS"));
				PdfPCell col5 = new PdfPCell(new Paragraph("nome_mecanico"));
				tabela2.addCell(col1);
				tabela2.addCell(col2);
				tabela2.addCell(col3);
				tabela2.addCell(col4);
				tabela2.addCell(col5);
				while (rs.next()) {
					tabela2.addCell(rs.getString(1));
					tabela2.addCell(rs.getString(2));
					tabela2.addCell(rs.getString(3));
					tabela2.addCell(rs.getString(4));
					tabela2.addCell(rs.getString(5));

				}
				document.add(tabela2);

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("aguardandoPeças.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	private void estoqueMin() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("estoqueMin.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("estoque Min:"));
			document.add(new Paragraph(" "));
			String readAguardandoAprovacao = "select produtos.idprod,produtos.nomeproduto,produtos.estoque,produtos.estoquemin,Fornecedores.razaosocial,Fornecedores.idfor,Fornecedores.tel from produtos inner join Fornecedores on Fornecedores.idfor = produtos.idfor  where estoquemin >= estoque;";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(readAguardandoAprovacao);
				ResultSet rs = pst.executeQuery();
				PdfPTable tabela2 = new PdfPTable(7);
				PdfPCell col1 = new PdfPCell(new Paragraph("idprod"));
				PdfPCell col2 = new PdfPCell(new Paragraph("nomeproduto"));
				PdfPCell col3 = new PdfPCell(new Paragraph("estoque"));
				PdfPCell col4 = new PdfPCell(new Paragraph("estoquemin"));
				PdfPCell col5 = new PdfPCell(new Paragraph("razaosocial"));
				PdfPCell col6 = new PdfPCell(new Paragraph("idfor"));
				PdfPCell col7 = new PdfPCell(new Paragraph("tel"));
				tabela2.addCell(col1);
				tabela2.addCell(col2);
				tabela2.addCell(col3);
				tabela2.addCell(col4);
				tabela2.addCell(col5);
				tabela2.addCell(col6);
				tabela2.addCell(col7);
				while (rs.next()) {
					tabela2.addCell(rs.getString(1));
					tabela2.addCell(rs.getString(2));
					tabela2.addCell(rs.getString(3));
					tabela2.addCell(rs.getString(4));
					tabela2.addCell(rs.getString(5));
					tabela2.addCell(rs.getString(6));
					tabela2.addCell(rs.getString(7));

				}
				document.add(tabela2);

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("estoqueMin.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
}
