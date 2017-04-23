package suffixtree.test;

import suffixtree.Ukkonen;
import suffixtree.SuffixTree;
import suffixtree.Arista;
import suffixtree.Nodo;
import util.MutableInt;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.List;


/* Clase para pruebas unitarias de Ukkonen */
public class TestUkkonen{

    /* Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    private static String banana = "banana";
    private static Ukkonen u = new Ukkonen(banana);
    private static Nodo n1 = new Nodo();
    private static Arista a = new Arista(n1, n1, 0, new MutableInt(5));
    private static Nodo n2 = new Nodo();
    private static Arista b = new Arista(n1, n2, 1, new MutableInt(5));
    private static Nodo n3 = new Nodo();
    private static Arista c = new Arista(n1, n3, 3, new MutableInt(5));
    private static Nodo n4 = new Nodo();
    private static Arista d = new Arista(n1, n4, 2, new MutableInt(5));
    
    /* Establecemos el Nodo activo del algoritmo */
    public TestUkkonen(){
	u.setActiveNode(n1);
    }
    
    /* Prueba para startsWith */
    @Test public void testStartsWith(){
	Assert.assertTrue(u.startsWith('b', a));
	Assert.assertFalse(u.startsWith('a', a));
    }

    /* Prueba para get */
    @Test public void testGet(){
	Assert.assertTrue(u.get(3, a) == 'a');
	Assert.assertFalse(u.get(0, a) == 'a');
    }

    /* Prueba para insertado sin arista activa */
    @Test public void testInsertadoSinActiva(){
	u.setActiveNode(n1);
	u.setActiveEdge(null);
	u.setActiveLength(0);
	Assert.assertTrue(u.insertado('a'));
	Assert.assertFalse(u.insertado('c'));
    }

    /* Prueba para insertado con arista activa */
    @Test public void testInsertadoConActiva(){
	Assert.assertTrue(u.insertado('n'));
	Assert.assertFalse(u.insertado('c'));
	u.setActiveEdge(b);
	u.setActiveLength(1);
    }

    /* Prueba para insertado con una arista que ya llegó al final */
    @Test public void testInsertadoFinal(){
	Arista x = new Arista(n1, n2, 0, new MutableInt(2));
	Arista y = new Arista(n2, n3, 3, new MutableInt(3));
	u.setActiveEdge(x);
	u.setActiveLength(3);
	Assert.assertTrue(u.insertado('a'));
    }

    /* Prueba para busca */
    @Test public void testBusca(){
	Assert.assertTrue(u.busca('w') == null);
	Assert.assertTrue(u.busca('n').equals(d));              
    }

    /* Prueba para split */
    @Test public void testSplit(){
	Ukkonen u2 = new Ukkonen("abcabx");
	Nodo raiz = new Nodo();
	u2.setActiveNode(raiz);
	Arista a1 = new Arista(raiz, new Nodo(), 0, new MutableInt(5)); //abcabx
	Arista a2 = new Arista(raiz, new Nodo(), 1, new MutableInt(5)); //bcabx
	Arista a3 = new Arista(raiz, new Nodo(), 2, new MutableInt(5)); //cabx
	u2.setActiveEdge(a1);
	u2.setActiveLength(2);
	u2.split(new MutableInt(5));
	Assert.assertTrue(a1.getFin().getValue() == 1);
	List<Arista> vecinos = a1.getHasta().getAristas();
	Assert.assertTrue(vecinos.size() == 2);
	Arista a4 = vecinos.get(0);
	Arista a5 = vecinos.get(1);
	Assert.assertTrue(a4.longitud() == 4);
	Assert.assertTrue(a5.longitud() == 1);
    }

    /* Prueba unitaria para ukkonen */
    @Test public void testUkkonen(){
	Ukkonen uk = new Ukkonen("banana");
	SuffixTree t = uk.ukkonen();
	List<String> l = t.subcadenas(t.getRaiz());
	Assert.assertTrue(l.size() == 7);
	uk = new Ukkonen("abcabxabcd");
	t = uk.ukkonen();
	l = t.subcadenas(t.getRaiz());
	Assert.assertTrue(l.size() == 11); 
    }
    
    
}
