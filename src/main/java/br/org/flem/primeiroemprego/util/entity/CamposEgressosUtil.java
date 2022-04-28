package br.org.flem.primeiroemprego.util.entity;

import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.entity.Deficiencia;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Formacao;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.RacaCor;
import br.org.flem.primeiroemprego.entity.Situacao;
import br.org.flem.primeiroemprego.entity.Vaga;
import br.org.flem.primeiroemprego.util.annotation.Alias;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emsilva
 */
public class CamposEgressosUtil implements Serializable {

    private static final Map<String, Method> METODOS_GET = new LinkedHashMap<String, Method>();
    private static final Map<String, Method> METODOS_SET = new LinkedHashMap<String, Method>();

    public static boolean contemCampo(String campo) {
        return METODOS_GET.containsKey(campo);
    }

    static {
        try {
            Alias a;
            for (Field field : Egresso.class.getDeclaredFields()) {
                if (field.isAnnotationPresent(Alias.class)) {
                    a = field.getAnnotation(Alias.class);
                    METODOS_GET.put(a.value(), Egresso.class.getDeclaredMethod("get" + capitalize(field.getName())));
                    METODOS_SET.put(a.value(), Egresso.class.getDeclaredMethod("set" + capitalize(field.getName()), field.getType()));

                    if (!a.valueAdicional().equals("")) {
                        METODOS_GET.put(a.valueAdicional(), Egresso.class.getDeclaredMethod("get" + capitalize(field.getName())));
                        METODOS_SET.put(a.valueAdicional(), Egresso.class.getDeclaredMethod("set" + capitalize(field.getName()), field.getType()));
                    }
                }
            }
            for (Field field : Vaga.class.getDeclaredFields()) {
                if (field.isAnnotationPresent(Alias.class)) {
                    a = field.getAnnotation(Alias.class);
                    METODOS_GET.put(a.value(), Vaga.class.getDeclaredMethod("get" + capitalize(field.getName())));
                    METODOS_SET.put(a.value(), Vaga.class.getDeclaredMethod("set" + capitalize(field.getName()), field.getType()));
                    if (!a.valueAdicional().equals("")) {
                        METODOS_GET.put(a.valueAdicional(), Vaga.class.getDeclaredMethod("get" + capitalize(field.getName())));
                        METODOS_SET.put(a.valueAdicional(), Vaga.class.getDeclaredMethod("set" + capitalize(field.getName()), field.getType()));
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CamposEgressosUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static List<String> camposPossiveis(List<String> remove) {
        List campos = new ArrayList(METODOS_GET.keySet());
        if (remove != null) {
            campos.removeAll(remove);
        }
        Collections.sort(campos);
        return campos;
    }

    public static List<String> camposPossiveis() {
        return camposPossiveis(null);
    }

    public static boolean igual(Egresso egresso, String campo, String valor) {
        String valorEgresso = getInformacao(egresso, campo);
        return valorEgresso != null && valor != null && valor.equals(valorEgresso) || valorEgresso == null && valor == null;
    }

    public static String getInformacao(Egresso egresso, String campo) {
        if (CamposEgressosUtil.contemCampo(campo)) {
            try {
                Object informacao;
                Method metodoDoCampo = METODOS_GET.get(campo);
                if (metodoDoCampo.getDeclaringClass().equals(Vaga.class)) {
                    informacao = metodoDoCampo.invoke(egresso.getVaga());
                } else {
                    informacao = metodoDoCampo.invoke(egresso);
                }
                if (informacao != null) {
                    if (metodoDoCampo.getReturnType().equals(Situacao.class)) {
                        return ((Situacao) informacao).getCategoria().getNome() + " - " + ((Situacao) informacao).getNome();
                    }
                    if (metodoDoCampo.getReturnType().equals(Date.class)) {
                        return Data.formataData(((Date) informacao));
                    }
                    if (metodoDoCampo.getReturnType().equals(Municipio.class)) {
                        return ((Municipio) informacao).getNome();
                    }
                    if (metodoDoCampo.getReturnType().equals(Demandante.class)) {
                        if (campo.equals("Sigla Demandante")) {
                            return ((Demandante) informacao).getSigla();
                        }
                        return ((Demandante) informacao).getNome();
                    }
                    if (metodoDoCampo.getReturnType().equals(Formacao.class)) {
                        return ((Formacao) informacao).getNome();
                    }
                    if (metodoDoCampo.getReturnType().equals(Deficiencia.class)) {
                        return ((Deficiencia) informacao).getNome();
                    }
                    if (metodoDoCampo.getReturnType().equals(RacaCor.class)) {
                        return ((RacaCor) informacao).getDescricao();
                    }
                    return informacao.toString();
                }
            } catch (Exception e) {
                Logger.getLogger(CamposEgressosUtil.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public static void setInformacao(Egresso egresso, String campo, Object valor) throws Exception {
        if (contemCampo(campo)) {
            Method metodoDoCampo = METODOS_SET.get(campo);
            if (metodoDoCampo.getDeclaringClass().equals(Vaga.class)) {
                metodoDoCampo.invoke(egresso.getVaga(), valor);
            } else {
                metodoDoCampo.invoke(egresso, valor);
            }
        }
    }

    public static void setInformacao(Egresso egresso, Map<String, Object> values) throws Exception {
        for (String campo : values.keySet()) {
            setInformacao(egresso, campo, values.get(campo));
        }

    }

    public static boolean campoSituacao(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(Situacao.class);
    }

    public static boolean campoDate(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(Date.class);
    }

    public static boolean campoMunicipio(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(Municipio.class);
    }

    public static boolean campoDemandante(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(Demandante.class);
    }

    public static boolean campoFormacao(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(Formacao.class);
    }

    public static boolean campoDeficiencia(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(Deficiencia.class);
    }

    public static boolean campoRacaCor(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(RacaCor.class);
    }

    public static boolean campoBoolean(String coluna) {
        return contemCampo(coluna) && METODOS_SET.get(coluna).getParameterTypes()[0].equals(Boolean.class);
    }

}
