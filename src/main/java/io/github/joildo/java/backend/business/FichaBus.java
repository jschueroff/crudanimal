/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.joildo.java.backend.business;

import io.github.joildo.java.backend.data.Ficha;

import io.github.joildo.java.backend.infra.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author aluno
 */
public class FichaBus {

    public Long inserir(Ficha chamado) {
        chamado.setDataRegistro(new Date());
       // chamado.setStatus(Status.NOVO);
       // chamado.setTipo(Tipo.SOLICITACAO);
//        chamado.setUsuario(UsuarioBus.selecionarAluno());
//        chamado.setUsuarioStatus(UsuarioBus.selecionarAluno());

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(chamado);
        t.commit();
        return chamado.getId();
    }

    public void alterar(Ficha chamado) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.merge(chamado);
        t.commit();
    }

    public void excluir(long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Ficha c = selecionar(id);

        Transaction t = s.beginTransaction();
        s.delete(c);
        t.commit();
    }

    public Ficha selecionar(long id) {
        return (Ficha) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Ficha.class, id);
    }

    public List<Ficha> listar() {
        return (List<Ficha>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Chamado")
                .list();
    }
}
