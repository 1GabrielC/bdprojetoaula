
package br.com.DTO;



public class UsuarioDTO {
    private int id_uauario;
    private String nome_usuario, login_usuario, senha_usuario, perfil_usuario;

    public String getPerfil_usuario() {
        return perfil_usuario;
    }

    public void setPerfil_usuario(String perfil_usuario) {
        this.perfil_usuario = perfil_usuario;
    }

    public int getId_uauario() {
        return id_uauario;
    }

    public void setId_uauario(int id_uauario) {
        this.id_uauario = id_uauario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getLogin_usuario() {
        return login_usuario;
    }

    public void setLogin_usuario(String login_usuario) {
        this.login_usuario = login_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }
    
}
