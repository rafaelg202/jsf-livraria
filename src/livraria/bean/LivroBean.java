package livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import livraria.dao.DAO;
import livraria.modelo.Autor;
import livraria.modelo.Livro;
import livraria.util.ForwardView;
import livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();	

	private Integer autorId;
	
	public Livro getLivro() {
		return livro;
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	
	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public void gravar() {
		System.out.println("Gravando livro! " + this.livro.getTitulo());
		
		if(livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um autor");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor"));
			return;
		}
		
		if(this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);			
		}else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);			
		}

		
		this.livro = new Livro();
	}
	
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = livro;
		new DAO<Livro>(Livro.class).atualiza(livro);
	}
	
	public String formAutor() {
		System.out.println("Chamando o form do Autor");
		return "autor?faces-redirect=true";
	}
	
//	public ForwardView formAutor() {
//		System.out.println("Chamando o form do Autor");
//		return new ForwardView("autor");
//	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		
		String valor = value.toString();
		if(!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria comecar com 1"));
		} 
	}
}
