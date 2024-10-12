package br.com.DAO;

import br.com.DTO.UsuarioDTO;
import br.com.views.TelaPrincipal;
import br.com.views.TelaUsuario;
import java.awt.Color;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class UsuarioDAO {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void logar(UsuarioDTO objusuarioDTO) {
        String sql = "select * from tb_usuarios where login = ? and senha = ?";
        conexao = ConexaoDAO.conector();

        try {
            // preparar a consulta no banco, em função ao que foi inserido nas caixas de texto
            pst = conexao.prepareStatement(sql);
            pst.setString(1, objusuarioDTO.getLogin_usuario());
            pst.setString(2, objusuarioDTO.getSenha_usuario());

//            executa a query
            rs = pst.executeQuery();
//            verifica se existe usuario
            if (rs.next()) {
                // obtem o conteúdo do atributo perfil
                String perfil = rs.getString(5);
//                System.out.println(perfil);

                //tratamento de perfil
                if (perfil.equals("admin")) {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    TelaPrincipal.MenuRel.setEnabled(true);
                    TelaPrincipal.subMenuUsuarios.setEnabled(true);
                    TelaPrincipal.lblUsuarioPrincipal.setText(rs.getString(2));
                    TelaPrincipal.lblUsuarioPrincipal.setForeground(Color.RED);
                    conexao.close();//Fechar a conexão                    
                } else {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    principal.lblUsuarioPrincipal.setText(rs.getString(2));
                    TelaPrincipal.lblUsuarioPrincipal.setForeground(Color.BLUE);
                    conexao.close();//Fechar a conexão   

                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuário e/ou senha invalidos");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "** tela Login ***" + e);
        }
    }

    public void inserirUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "insert into tb_usuarios (id_usuario, usuario, login, senha, perfil)"
                + " values (?, ?, ?, ?, ?)";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_uauario());
            pst.setString(2, objUsuarioDTO.getNome_usuario());
            pst.setString(3, objUsuarioDTO.getLogin_usuario());
            pst.setString(4, objUsuarioDTO.getSenha_usuario());
            pst.setString(5, objUsuarioDTO.getPerfil_usuario());
            int add  = pst.executeUpdate();
            if (add > 0) {
                pesquisaAuto();
                pst.close();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso! ");
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, " Método Inserir " + e);
        }
    }

    public void pesquisar(UsuarioDTO objUsuarioDTO) {
        String sql = "select * from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_uauario());
            rs = pst.executeQuery();
            if (rs.next()) {
                TelaUsuario.txtNomeUsu.setText(rs.getString(2));
                TelaUsuario.txtLoginUsu.setText(rs.getString(3));
                TelaUsuario.txtSenhaUsu.setText(rs.getString(4));
                TelaUsuario.cboPerfilUsu.setSelectedItem(rs.getString(5));
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método Pesquisar" + e);
        }
    }

    public void pesquisaAuto() {
        String sql = "select * from tb_usuarios";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) TelaUsuario.TbUsuarios.getModel();
            model.setNumRows(0);

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("usuario");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                String perfil = rs.getString("perfil");
                model.addRow(new Object[]{id, nome, login, senha, perfil});
            }
            conexao.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método Pesquisar Automático " + e);
        }
    }

    //Método editar
    public void editar(UsuarioDTO objUsuarioDTO) {
        String sql = "update tb_usuarios set usuario = ?, login = ?, senha = ?, perfil = ? where id_usuario = ?";
        conexao = ConexaoDAO.conector();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(5, objUsuarioDTO.getId_uauario());
            pst.setString(4, objUsuarioDTO.getPerfil_usuario());
            pst.setString(3, objUsuarioDTO.getSenha_usuario());
            pst.setString(2, objUsuarioDTO.getLogin_usuario());
            pst.setString(1, objUsuarioDTO.getNome_usuario());
            int add = pst.executeUpdate();
            if (add > 0) {
                JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
                pesquisaAuto();
                conexao.close();
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método editar " + e);
        }
    }

    //Método deletar
    public void deletar(UsuarioDTO objUsuarioDTO) {
        String sql = "delete from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_uauario());
            int del = pst.executeUpdate();
            if (del > 0) {
                JOptionPane.showMessageDialog(null, " Usuário deletado com sucesso!");
                pesquisaAuto();
                conexao.close();
                limparCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método deletar " + e);
        }
    }

    public void limparCampos() {
        TelaUsuario.txtIdUsu.setText(null);
        TelaUsuario.txtLoginUsu.setText(null);
        TelaUsuario.txtNomeUsu.setText(null);
        TelaUsuario.txtSenhaUsu.setText(null);
        TelaUsuario.cboPerfilUsu.setSelectedItem(1);
    }

}
