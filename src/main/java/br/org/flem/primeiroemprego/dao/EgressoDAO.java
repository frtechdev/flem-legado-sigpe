package br.org.flem.primeiroemprego.dao;

import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.primeiroemprego.dto.FiltroEgressoDTO;
import br.org.flem.primeiroemprego.dto.FiltroFrequencia;
import br.org.flem.primeiroemprego.dto.ListaFrequenciaDTO;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.ListaEgressoDTO;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Vaga;
import br.org.flem.primeiroemprego.envers.VersaoDosDados;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.primefaces.model.SortOrder;

/**
 *
 * @author AJLima
 */
@ManagedBean
@ViewScoped
public class EgressoDAO extends GenericDAO<Egresso, Long> {

    public EgressoDAO() throws Exception {
        super(Egresso.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    @Override
    public List<Egresso> obterLista() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        root.fetch("vaga");
        criteria.select(root);
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Egresso> obterPorID(List<Long> ids) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        criteria.where(root.get("id").in(ids));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<BigInteger> obterEgressosPorModeloDeOficio(ModeloDeOficio modelodeOficio) {
        Query query = getEntityManager().createNativeQuery("Select m.id_egresso from ModeloDeOficio_EgressosPendentes m where m.id_documento = " + modelodeOficio.getId());
        return query.getResultList();
    }

    public List<ListaEgressoDTO> obterListaEgressoDTO() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ListaEgressoDTO> criteria = cb.createQuery(ListaEgressoDTO.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        Join joinMunicipioEscola = root.join("municipioEscola", JoinType.LEFT);
        Join joinMunicipioEgresso = root.join("municipio", JoinType.LEFT);
        Join joinDeficiencia = root.join("deficiencia", JoinType.LEFT);
        Join joinFormacao = root.join("formacao", JoinType.LEFT);
        Join joinEixoDeFormacao = joinFormacao.join("eixoDeFormacao", JoinType.LEFT);

        Join joinVaga = root.join("vaga", JoinType.INNER);
        Join joinMunicipioDaVaga = joinVaga.join("municipio", JoinType.LEFT);
        Join joinTerritorio = joinMunicipioDaVaga.join("territorio", JoinType.LEFT);
        Join joinMunicipioUnidadeDeLotacao = joinVaga.join("municipioUnidadeDeLotacao", JoinType.LEFT);

        Join joinDemandante = joinVaga.join("demandante", JoinType.LEFT);
        Join joinSituacao = joinVaga.join("situacao", JoinType.LEFT);
        Join joinCategoria = joinSituacao.join("categoria", JoinType.LEFT);
        Join joinEscRegionais = joinMunicipioDaVaga.join("escritorioRegional", JoinType.LEFT);

        criteria.select(cb.construct(ListaEgressoDTO.class,
                root.get("id"),
                root.get("nome"),
                root.get("cpf"),
                root.get("rg"),
                root.get("racaCor"),
                joinDeficiencia.get("nome"),
                root.get("dataNascimento"),
                root.get("cep"),
                root.get("bairro"),
                root.get("endereco"),
                root.get("complemento"),
                root.get("numero"),
                joinMunicipioEgresso.get("nome"),
                root.get("dddTelefone1"),
                root.get("telefone1"),
                root.get("dddTelefone2"),
                root.get("telefone2"),
                root.get("dddCelular"),
                root.get("celular"),
                root.get("dddContato"),
                root.get("contato"),
                root.get("emailParticular"),
                root.get("emailSecundario"),
                root.get("matriculaFlem"),
                root.get("observacao"),
                root.get("sexo"),
                root.get("matriculaSAEB"),
                joinDemandante.get("nome"),
                joinDemandante.get("sigla"),
                joinVaga.get("instituicao"),
                joinVaga.get("unidadeDeLotacao"),
                joinVaga.get("logradouroUnidadeDeLotacao"),
                joinVaga.get("bairroUnidadeDeLotacao"),
                joinMunicipioUnidadeDeLotacao.get("nome"),
                joinVaga.get("cepUnidadeDeLotacao"),
                joinVaga.get("pontoFocalNaUnidade"),
                joinVaga.get("telefone1PontoFocal"),
                joinVaga.get("telefone2PontoFocal"),
                joinVaga.get("telefone3PontoFocal"),
                joinVaga.get("telefone4PontoFocal"),
                joinVaga.get("telefone5PontoFocal"),
                joinVaga.get("emailPontoFocal"),
                joinTerritorio.get("nome"),
                joinMunicipioDaVaga.get("nome"),
                root.get("distanciaEntreMunicipios"),
                joinVaga.get("dataConvocacaoSETRESINE"),
                joinVaga.get("remessaSETRESINE"),
                joinVaga.get("dataRemessaSETRESINE"),
                joinVaga.get("mesRemessaSETRESINE"),
                joinCategoria.get("nome"),
                joinCategoria.get("cor"),
                joinSituacao.get("nome"),
                joinSituacao.get("cor"),
                root.get("anamnese"),
                joinVaga.get("dataInicioAtividades"),
                joinVaga.get("publicacaoDiarioOficial"),
                joinVaga.get("dataDeEnvioDaSituacaoParaSETRESINE"),
                joinFormacao.get("nome"),
                joinEixoDeFormacao.get("nome"),
                joinMunicipioEscola.get("nome"),
                root.get("valeAlimentacao"),
                root.get("valeTransporte"),
                root.get("aso"),
                root.get("tamanhoDeCamisa"),
                root.get("fardamentoRecebido"),
                root.get("escolaconclusao"),
                root.get("fichaAdmissao"),
                root.get("ctpsPendente"),
                root.get("cpfPendente"),
                root.get("comprovanteresidenciaPendente"),
                root.get("rgPendente"),
                root.get("cartaCidadaoPendente"),
                root.get("tituloEleitorPendente"),
                root.get("comprovanteVotacaoPendente"),
                root.get("diplomaPendente"),
                root.get("curriculoPendente"),
                root.get("dadosbancariosPendente"),
                root.get("foto3x4Pendente"),
                root.get("asoPendente"),
                root.get("certidaoCasamentoPendente"),
                root.get("certidaoNascimentoPendente"),
                root.get("comprovanteEscolaridadePendente"),
                root.get("reservistaPendente"),
                root.get("cartaoVacinaPendente"),
                root.get("contratoTrabalhoFlem"),
                root.get("vale_Transporte"),
                root.get("vale_Alimentacao"),
                root.get("plano_Saude"),
                root.get("cracha"),
                root.get("estagioConcluido"),
                root.get("carteiraAssinada"),
                root.get("pis"),
                root.get("ctps"),
                joinEscRegionais.get("nome"),
                root.get("cursoSuperior"),
                root.get("tipoInstituicaoSuperior"),
                root.get("nomeInstituicaoSuperior"),
                root.get("nomeCursoGraduacao"),
                root.get("modalidadeGraduacao"),
                root.get("anoMatricula"),
                root.get("semestre"),
                root.get("rendaPPEAjuda"),
                root.get("dataRespostaSemestre"),
                root.get("dataAdmissao"),
                root.get("cursoSuperiorConcluido"),
                root.get("anoConclusaoCurso"),
                root.get("pretendeFazerCursoSuperior"),
                root.get("cursoTecnicoProfissionalizante"),
                root.get("tipoInstituicaoTecnica"),
                root.get("nomeInstituicaoTecnico"),
                root.get("nomeCursoTecnico")
        ));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        List<ListaEgressoDTO> egressoDTOs = getEntityManager().createQuery(criteria).getResultList();
        Map<Long, ListaEgressoDTO> mapEgressosDTO = new HashMap<>();
        for (ListaEgressoDTO eDTO : egressoDTOs) {
            mapEgressosDTO.put(eDTO.getIdEgresso(), eDTO);
        }

        //Obtendo a Lista de Eventos para preencher o DTO do egresso
        CriteriaQuery<Tuple> criteriaEventos = cb.createTupleQuery();
        Root<Egresso> egressoRoot = criteriaEventos.from(Egresso.class);
        criteriaEventos.multiselect(egressoRoot.get("id"), egressoRoot.join("eventosParticipados").get("id").get("evento").get("nome"));
        List<Tuple> idEgressosEventos = getEntityManager().createQuery(criteriaEventos).getResultList();
        for (Tuple tuple : idEgressosEventos) {
            mapEgressosDTO.get((Long) tuple.get(0)).getEventos().add((String) tuple.get(1));
        }

        //Obtendo as Listas para preencher o DTO do egresso
        CriteriaQuery<Tuple> criteriaListas = cb.createTupleQuery();
        egressoRoot = criteriaListas.from(Egresso.class);
        criteriaListas.multiselect(egressoRoot.get("id"), egressoRoot.join("estadoNasListas").get("lista").get("nome"));
        List<Tuple> idEgressosListas = getEntityManager().createQuery(criteriaListas).getResultList();
        for (Tuple tuple : idEgressosListas) {
            mapEgressosDTO.get((Long) tuple.get(0)).getListas().add((String) tuple.get(1));
        }

        return egressoDTOs;
    }
    
    public List<ListaEgressoDTO> obterListaEgressoDTO(FiltroEgressoDTO filtro) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ListaEgressoDTO> criteria = cb.createQuery(ListaEgressoDTO.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        Join joinFormacao = root.join("formacao", JoinType.INNER);
        Join joinVaga = root.join("vaga", JoinType.INNER);
        Join joinMunicipioDaVaga = joinVaga.join("municipio", JoinType.INNER);
        Join joinDemandante = joinVaga.join("demandante", JoinType.LEFT);
        Join joinSituacao = joinVaga.join("situacao", JoinType.LEFT);
        Join joinCategoria = joinSituacao.join("categoria", JoinType.LEFT);
        Join joinEscRegionais = joinMunicipioDaVaga.join("escritorioRegional", JoinType.LEFT);

        criteria.select(cb.construct(ListaEgressoDTO.class,
                root.get("id"), 
                root.get("nome"),
                root.get("cpf"),
                root.get("matriculaFlem"),
                joinDemandante.get("nome"), 
                joinMunicipioDaVaga.get("nome"), 
                joinCategoria.get("nome"), 
                joinCategoria.get("cor"),
                joinSituacao.get("nome"),
                joinSituacao.get("cor"),
                joinFormacao.get("nome"), 
                joinEscRegionais.get("nome")
        ));
        if( filtro != null ){
            List<Predicate> predicates = new ArrayList<>();
            if(filtro.getCategorias() != null && !filtro.getCategorias().isEmpty()){
                predicates.add(joinVaga.get("situacao").get("categoria").in(filtro.getCategorias()));
            }
            if(!StringUtils.isEmpty(filtro.getNome())){
                predicates.add(cb.like(root.get("nome"), "%" + filtro.getNome() + "%"));
            }
            if(!StringUtils.isEmpty(filtro.getCpf()) ){
                predicates.add(cb.equal(root.get("cpf"), filtro.getCpf()));
            }
            if(!StringUtils.isEmpty(filtro.getMatriculaFlem())){
                predicates.add(cb.equal(root.get("matriculaFlem"), filtro.getMatriculaFlem()));
            }
            if(filtro.getDemandantes()!= null && !filtro.getDemandantes().isEmpty()){
                predicates.add(joinVaga.get("demandante").in(filtro.getDemandantes()));
            }
            if(filtro.getMunicipios()!= null && !filtro.getMunicipios().isEmpty()){
                predicates.add(joinVaga.get("municipio").in(filtro.getMunicipios()));
            }
            if(filtro.getEscritoriosRegionais()!= null && !filtro.getEscritoriosRegionais().isEmpty()){
                predicates.add(joinMunicipioDaVaga.get("escritorioRegional").in(filtro.getEscritoriosRegionais()));
            }
            if(filtro.getFormacoes()!= null && !filtro.getFormacoes().isEmpty() ){
                predicates.add(root.get("formacao").in(filtro.getFormacoes()));
            }
            criteria.where(predicates.toArray(new Predicate[]{}));
        }
        criteria.orderBy(cb.asc(root.get("nome")));
        return getEntityManager().createQuery(criteria).getResultList();

    }

    public Egresso obterEgressoPorMatricula(Integer matricula) {
        Egresso cont = new Egresso();
        Query query;
        StringBuilder str = new StringBuilder("from Egresso t where 1=1");
        if (matricula != null) {
            str.append(" AND matriculaflem = :matric");
        }
        query = getEntityManager().createQuery(str.toString());

        if (matricula != null) {
            query.setParameter("matric", matricula);
        }

        List<Egresso> lista = query.getResultList();

        for (Egresso egresso : lista) {
            cont = egresso;
        }
        return cont;
    }

    public Boolean existePorCPF(String cpf) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Integer> criteria = cb.createQuery(Integer.class);
            Root<Egresso> root = criteria.from(Egresso.class);
            criteria.where(cb.equal(root.get("cpf"), cpf));
            criteria.select(root.<Integer>get("id"));
            return !getEntityManager().createQuery(criteria).getResultList().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public Egresso obterPorCPF(String cpf) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
            Root<Egresso> root = criteria.from(Egresso.class);
            criteria.where(cb.equal(root.get("cpf"), cpf));
            Egresso c = getEntityManager().createQuery(criteria).getSingleResult();
            if (c.getMatriculaFlem() != null) {
                c.setFuncionario(new RHServico().obterFuncionarioPorMatricula(Integer.parseInt(c.getMatriculaFlem())));
            }
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Egresso> obterEgressosPorCPF(String cpf) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
            Root<Egresso> root = criteria.from(Egresso.class);
            criteria.where(cb.equal(root.get("cpf"), cpf));
            List<Egresso> egs = getEntityManager().createQuery(criteria).getResultList();
            for (Egresso c : egs) {
                if (c.getMatriculaFlem() != null) {
                    c.setFuncionario(new RHServico().obterFuncionarioPorMatricula(Integer.parseInt(c.getMatriculaFlem())));
                }
            }
            return egs;
        } catch (Exception e) {
            return null;
        }
    }

    public Egresso obterPorCPFAtivo(String cpf) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
            Root<Egresso> root = criteria.from(Egresso.class);
            root.fetch("vaga");
            criteria.select(root);
            criteria.where(cb.equal(root.get("cpf"), cpf),
                    root.get("vaga").get("situacao").get("categoria").get("id").in(1, 2, 3, 4, 5, 7));
            Egresso c = getEntityManager().createQuery(criteria).getSingleResult();
            if (c.getMatriculaFlem() != null) {
                c.setFuncionario(new RHServico().obterFuncionarioPorMatricula(Integer.parseInt(c.getMatriculaFlem())));
            }

            return c;

        } catch (Exception e) {

            return null;
        }
    }

    public List<String> obterRespostasDaVaga(String campo) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        criteria.distinct(true);
        Root<Vaga> root = criteria.from(Vaga.class);
        criteria.select(root.<String>get(campo));
        criteria.where(cb.isNotNull(root.get(campo)));
        criteria.orderBy(cb.asc(root.get(campo)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<String> obterRespostasDoCampo(String campo) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        criteria.distinct(true);
        Root<Egresso> root = criteria.from(Egresso.class);
        criteria.select(root.<String>get(campo));
        criteria.where(cb.isNotNull(root.get(campo)));
        criteria.orderBy(cb.asc(root.get(campo)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    @Override
    public Egresso obterPorPK(Long id) {
        Egresso c = super.obterPorPK(id);
        if (c != null && c.getMatriculaFlem() != null) {
            c.setFuncionario(new RHServico().obterFuncionarioPorMatricula(Integer.parseInt(c.getMatriculaFlem())));
        }
        return c;
    }

    public void carregarHistoricoDeVaga(Egresso egresso) {
        if (egresso != null) {
            AuditQuery auditQuery = getAuditReader().createQuery().forRevisionsOfEntity(Vaga.class, false, false);
            auditQuery.add(AuditEntity.property("egresso").eq(egresso));
            List<Object[]> dados = auditQuery.getResultList();
            egresso.setHistoricoVaga(new ArrayList<>());
            if (!dados.isEmpty()) {
                for (Object[] arr : dados) {
                    ((Vaga) arr[0]).setUsuarioAlteracao(((VersaoDosDados) arr[1]).getLogin());
                    ((Vaga) arr[0]).setDataAlteracao(new Date(((VersaoDosDados) arr[1]).getTimestamp()));
                    egresso.getHistoricoVaga().add((Vaga) arr[0]);
                }
            }
        }
    }

    public List<Egresso> obterNaoContratados() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        criteria.where(cb.or(cb.isNull(root.get("matriculaFlem")), cb.equal(root.get("matriculaFlem"), "")));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Egresso> pendentesDeCalculoDeDistanciaEntreMunicipios() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        criteria.where(cb.isNull(root.get("distanciaEntreMunicipios")));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(getEntityManager());
    }

    public List<Egresso> obterEgressosCategoriasDefinidas() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Egresso> criteria = cb.createQuery(Egresso.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        root.fetch("vaga");
        criteria.select(root);
        criteria.where(root.get("vaga").get("situacao").get("categoria").get("id").in(1, 2, 4, 5));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<ListaEgressoDTO> obterListaPaginada(int firstResult, int maxResult, String order, SortOrder sortOrder, Map<String, Object> filtro) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ListaEgressoDTO> criteria = cb.createQuery(ListaEgressoDTO.class);
        Root<Egresso> root = criteria.from(Egresso.class);
        Join joinMunicipioEscola = root.join("municipioEscola", JoinType.LEFT);
        Join joinMunicipioEgresso = root.join("municipio", JoinType.LEFT);
        Join joinDeficiencia = root.join("deficiencia", JoinType.LEFT);
        Join joinFormacao = root.join("formacao", JoinType.LEFT);
        Join joinEixoDeFormacao = joinFormacao.join("eixoDeFormacao", JoinType.LEFT);

        Join joinVaga = root.join("vaga", JoinType.INNER);
        Join joinMunicipioDaVaga = joinVaga.join("municipio", JoinType.LEFT);
        Join joinTerritorio = joinMunicipioDaVaga.join("territorio", JoinType.LEFT);
        Join joinMunicipioUnidadeDeLotacao = joinVaga.join("municipioUnidadeDeLotacao", JoinType.LEFT);

        Join joinDemandante = joinVaga.join("demandante", JoinType.LEFT);
        Join joinSituacao = joinVaga.join("situacao", JoinType.LEFT);
        Join joinCategoria = joinSituacao.join("categoria", JoinType.LEFT);
        Join joinEscRegionais = joinMunicipioDaVaga.join("escritorioRegional", JoinType.LEFT);

        criteria.select(cb.construct(ListaEgressoDTO.class,
                root.get("id"),
                root.get("nome"),
                root.get("cpf"),
                root.get("rg"),
                root.get("racaCor"),
                joinDeficiencia.get("nome"),
                root.get("dataNascimento"),
                root.get("cep"),
                root.get("bairro"),
                root.get("endereco"),
                root.get("complemento"),
                root.get("numero"),
                joinMunicipioEgresso.get("nome"),
                root.get("dddTelefone1"),
                root.get("telefone1"),
                root.get("dddTelefone2"),
                root.get("telefone2"),
                root.get("dddCelular"),
                root.get("celular"),
                root.get("dddContato"),
                root.get("contato"),
                root.get("emailParticular"),
                root.get("emailSecundario"),
                root.get("matriculaFlem"),
                root.get("observacao"),
                root.get("sexo"),
                root.get("matriculaSAEB"),
                joinDemandante.get("nome"),
                joinDemandante.get("sigla"),
                joinVaga.get("instituicao"),
                joinVaga.get("unidadeDeLotacao"),
                joinVaga.get("logradouroUnidadeDeLotacao"),
                joinVaga.get("bairroUnidadeDeLotacao"),
                joinMunicipioUnidadeDeLotacao.get("nome"),
                joinVaga.get("cepUnidadeDeLotacao"),
                joinVaga.get("pontoFocalNaUnidade"),
                joinVaga.get("telefone1PontoFocal"),
                joinVaga.get("telefone2PontoFocal"),
                joinVaga.get("telefone3PontoFocal"),
                joinVaga.get("telefone4PontoFocal"),
                joinVaga.get("telefone5PontoFocal"),
                joinVaga.get("emailPontoFocal"),
                joinTerritorio.get("nome"),
                joinMunicipioDaVaga.get("nome"),
                root.get("distanciaEntreMunicipios"),
                joinVaga.get("dataConvocacaoSETRESINE"),
                joinVaga.get("remessaSETRESINE"),
                joinVaga.get("dataRemessaSETRESINE"),
                joinVaga.get("mesRemessaSETRESINE"),
                joinCategoria.get("nome"),
                joinCategoria.get("cor"),
                joinSituacao.get("nome"),
                joinSituacao.get("cor"),
                root.get("anamnese"),
                joinVaga.get("dataInicioAtividades"),
                joinVaga.get("publicacaoDiarioOficial"),
                joinVaga.get("dataDeEnvioDaSituacaoParaSETRESINE"),
                joinFormacao.get("nome"),
                joinEixoDeFormacao.get("nome"),
                joinMunicipioEscola.get("nome"),
                root.get("valeAlimentacao"),
                root.get("valeTransporte"),
                root.get("aso"),
                root.get("tamanhoDeCamisa"),
                root.get("fardamentoRecebido"),
                root.get("escolaconclusao"),
                root.get("fichaAdmissao"),
                root.get("ctpsPendente"),
                root.get("cpfPendente"),
                root.get("comprovanteresidenciaPendente"),
                root.get("rgPendente"),
                root.get("cartaCidadaoPendente"),
                root.get("tituloEleitorPendente"),
                root.get("comprovanteVotacaoPendente"),
                root.get("diplomaPendente"),
                root.get("curriculoPendente"),
                root.get("dadosbancariosPendente"),
                root.get("foto3x4Pendente"),
                root.get("asoPendente"),
                root.get("certidaoCasamentoPendente"),
                root.get("certidaoNascimentoPendente"),
                root.get("comprovanteEscolaridadePendente"),
                root.get("reservistaPendente"),
                root.get("cartaoVacinaPendente"),
                root.get("contratoTrabalhoFlem"),
                root.get("vale_Transporte"),
                root.get("vale_Alimentacao"),
                root.get("plano_Saude"),
                root.get("cracha"),
                root.get("estagioConcluido"),
                root.get("carteiraAssinada"),
                root.get("pis"),
                root.get("ctps"),
                joinEscRegionais.get("nome"),
                root.get("cursoSuperior"),
                root.get("tipoInstituicaoSuperior"),
                root.get("nomeInstituicaoSuperior"),
                root.get("nomeCursoGraduacao"),
                root.get("modalidadeGraduacao"),
                root.get("anoMatricula"),
                root.get("semestre"),
                root.get("rendaPPEAjuda"),
                root.get("dataRespostaSemestre"),
                root.get("dataAdmissao"),
                root.get("cursoSuperiorConcluido"),
                root.get("anoConclusaoCurso"),
                root.get("pretendeFazerCursoSuperior"),
                root.get("cursoTecnicoProfissionalizante"),
                root.get("tipoInstituicaoTecnica"),
                root.get("nomeInstituicaoTecnico"),
                root.get("nomeCursoTecnico")
        ));
        if (filtro.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : filtro.keySet()) {
                Predicate restNome;
                if (key.equalsIgnoreCase("cpf") || key.equalsIgnoreCase("sexo") || key.equalsIgnoreCase("matriculaFLEM")) {
                    restNome = cb.equal(root.get(key), filtro.get(key));
                } else {
                    String like = (String) filtro.get(key);
                    if (key.equalsIgnoreCase("formacao")) {
                        restNome = cb.like(joinFormacao.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("demandante")) {
                        restNome = cb.like(joinDemandante.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("categoria")) {
                        restNome = cb.like(joinCategoria.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("municipioDaVaga")) {
                        restNome = cb.like(joinMunicipioDaVaga.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("situacao")) {
                        restNome = cb.like(joinSituacao.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("escRegional")) {
                        restNome = cb.like(joinEscRegionais.get("nome"), "%" + like.trim() + "%");
                    } else {
                        restNome = cb.like(root.get(key), "%" + like.trim() + "%");
                    }
                }
                predicates.add(restNome);
            }

            criteria.where(predicates.toArray(new Predicate[]{}));
        }

        if (StringUtils.isNotEmpty(order)) {
            if (sortOrder.ASCENDING.equals(sortOrder)) {
                if ("demandante".equalsIgnoreCase(order)) {
                    criteria.orderBy(cb.asc(joinDemandante.get("nome")));
                } else {
                    criteria.orderBy(cb.asc(root.get(order)));
                }
            } else if ("demandante".equalsIgnoreCase(order)) {
                criteria.orderBy(cb.desc(joinDemandante.get("nome")));
            } else {
                criteria.orderBy(cb.desc(root.get(order)));
            }
        }

        TypedQuery<ListaEgressoDTO> tq = getEntityManager().createQuery(criteria);

        tq.setFirstResult(firstResult);
        tq.setMaxResults(maxResult);

        List<ListaEgressoDTO> egressoDTOs = tq.getResultList();

        return egressoDTOs;
    }

    public int obterQuantidade(Map<String, Object> filtro) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);

        Root<Egresso> root = criteria.from(Egresso.class);
        Join joinFormacao = root.join("formacao", JoinType.LEFT);
        Join joinVaga = root.join("vaga", JoinType.INNER);
        Join joinDemandante = joinVaga.join("demandante", JoinType.LEFT);
        Join joinSituacao = joinVaga.join("situacao", JoinType.LEFT);
        Join joinCategoria = joinSituacao.join("categoria", JoinType.LEFT);
        Join joinMunicipioDaVaga = joinVaga.join("municipio", JoinType.LEFT);
        Join joinEscRegionais = joinMunicipioDaVaga.join("escritorioRegional", JoinType.LEFT);

        criteria.select(cb.count(root.join("vaga", JoinType.INNER)));
        if (filtro.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : filtro.keySet()) {
                Predicate restNome;
                if (key.equalsIgnoreCase("cpf") || key.equalsIgnoreCase("sexo")) {
                    restNome = cb.equal(root.get(key), filtro.get(key));
                } else {
                    String like = (String) filtro.get(key);
                    if (key.equalsIgnoreCase("formacao")) {
                        restNome = cb.like(joinFormacao.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("demandante")) {
                        restNome = cb.like(joinDemandante.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("categoria")) {
                        restNome = cb.like(joinCategoria.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("municipioDaVaga")) {
                        restNome = cb.like(joinMunicipioDaVaga.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("situacao")) {
                        restNome = cb.like(joinSituacao.get("nome"), "%" + like.trim() + "%");
                    } else if (key.equalsIgnoreCase("escRegional")) {
                        restNome = cb.like(joinEscRegionais.get("nome"), "%" + like.trim() + "%");

                    } else {
                        restNome = cb.like(root.get(key), "%" + like.trim() + "%");
                    }
                }
                predicates.add(restNome);
            }

            criteria.where(predicates.toArray(new Predicate[]{}));
        }
        Long count = getEntityManager().createQuery(criteria).getSingleResult();

        if (count != null) {
            return count.intValue();
        }
        return 0;

    }

    /**
     * Obter lista de beneficiários que participarão do Evento
     *
     * @author <code>tscortes@flem.org.br</code>
     * @param filtro FiltroFrequencia
     * @return listaFrequencia List
     */
    public List<ListaFrequenciaDTO> obterListaDeFrequencia(FiltroFrequencia filtro) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<ListaFrequenciaDTO> criteria = cb.createQuery(ListaFrequenciaDTO.class);

        Root<Egresso> root = criteria.from(Egresso.class);

        Join joinVaga = root.join("vaga", JoinType.INNER);
        Join joinDemandante = joinVaga.join("demandante", JoinType.INNER);
        Join joinMunicipioVaga = joinVaga.join("municipio", JoinType.INNER);
        Join joinTerritorio = joinMunicipioVaga.join("territorio", JoinType.INNER);
        Join joinEscritorio = joinMunicipioVaga.join("escritorioRegional", JoinType.LEFT);
        Join joinSituacao = joinVaga.join("situacao", JoinType.INNER);
        Join joinCategoriaSituacao = joinSituacao.join("categoria", JoinType.INNER);

        criteria.select(cb.construct(ListaFrequenciaDTO.class,
                joinEscritorio.get("nome"),
                joinTerritorio.get("nome"),
                joinMunicipioVaga.get("nome"),
                joinDemandante.get("nome"),
                joinVaga.get("unidadeDeLotacao"),
                root.get("nome"),
                root.get("cpf"),
                root.get("formacao").get("nome"),
                root.get("emailParticular"),
                joinCategoriaSituacao.get("nome"),
                root.get("matriculaFlem"),
                joinDemandante.get("sigla")
        ));

        if (filtro != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.getCpfs() != null && !filtro.getCpfs().isEmpty()) {
                predicates.add(root.get("cpf").in(filtro.getCpfs()));
            }
            if (filtro.getDemandantes() != null && !filtro.getDemandantes().isEmpty()) {
                predicates.add(joinVaga.get("demandante").in(filtro.getDemandantes()));
            }

            if (filtro.getEscritoriosRegionais() != null && !filtro.getEscritoriosRegionais().isEmpty()) {
                predicates.add(joinMunicipioVaga.get("escritorioRegional").in(filtro.getEscritoriosRegionais()));
            }

            if (filtro.getTerritorios() != null && !filtro.getTerritorios().isEmpty()) {
                predicates.add(joinMunicipioVaga.get("territorio").in(filtro.getTerritorios()));
            }

            if (filtro.getMunicipios() != null && !filtro.getMunicipios().isEmpty()) {
                predicates.add(joinVaga.get("municipio").in(filtro.getMunicipios()));
            }

            if (filtro.getCategorias() != null && !filtro.getCategorias().isEmpty()) {
                predicates.add(joinSituacao.get("categoria").in(filtro.getCategorias()));
            }

            if (!predicates.isEmpty()) {
                criteria.where(predicates.toArray(new Predicate[]{}));
            }
        }
        criteria.orderBy(
                cb.asc(joinEscritorio.get("nome")),
                cb.asc(joinTerritorio.get("nome")),
                cb.asc(joinMunicipioVaga.get("nome")),
                cb.asc(joinDemandante.get("nome")),
                cb.asc(joinVaga.get("unidadeDeLotacao")),
                cb.asc(root.get("nome")));

        return getEntityManager().createQuery(criteria).getResultList();
    }

}
