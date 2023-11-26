import java.util.Scanner;

public class requerido {
    public static void main(String[] args) throws Exception {
        // Parte 1 definicion de variables
        int numeroProposiciones;
        String[][] tablaVerdad;
        int tamFilas;
        Scanner ingresoDatos = new Scanner(System.in);
        System.out.print("Ingrese el numero de proposiciones: ");
        numeroProposiciones = Integer.parseInt(ingresoDatos.nextLine());
        tablaVerdad = new String[1 + (int) Math.pow(2, numeroProposiciones)][numeroProposiciones + 5];
        // Tamanio de las filas
        tamFilas = 1 + (int) Math.pow(2, numeroProposiciones);
        // Para poner los nombres en las columnas con las operaciones que se hacen
        char nombreProposicion = 97;
        // llena la tabla con los valores de verdad
        tablaVerdad = llenarTabla(tablaVerdad, numeroProposiciones, tamFilas, 0, nombreProposicion);
        // Operaciones
        negacion(tablaVerdad, numeroProposiciones);
        disyuncion(tablaVerdad, numeroProposiciones + 1);
        conjuncion(tablaVerdad, numeroProposiciones + 2);
        dobleImplicacion(tablaVerdad, numeroProposiciones +3);
        imprimirTablaVerdad(tablaVerdad);
    }

    /**
     * Este metodo me ayudara a imprimir la tabla de valor de verdad
     * 
     * @param tablaVerdad
     */
    public static void imprimirTablaVerdad(String[][] tablaVerdad) {
        for (int i = 0; i < tablaVerdad.length; i++) {
            for (int j = 0; j < tablaVerdad[0].length; j++) {
                System.out.print(tablaVerdad[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Metodo para llenar la tabla de verdad (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param numeroProposiciones
     * @param tamFilas
     * @param columnaLlenar
     * @param nombreProposicion
     * @return
     */
    public static String[][] llenarTabla(String[][] tablaVerdad, int numeroProposiciones, int tamFilas,
            int columnaLlenar,
            char nombreProposicion) {
        // Variable utilizada para reiniciar el patron de V y F
        int reiniciador = 0;
        // Pone el nombre a la columna
        tablaVerdad[0][columnaLlenar] = String.valueOf(nombreProposicion);
        // Verifica si se sobrepasa las columnas a llenar para que se pueda parar la
        // recursividad
        if (columnaLlenar < numeroProposiciones) {
            // Recorre la columna para llenar
            for (int i = 1; i < tablaVerdad.length; i++) {
                // Verificamos si supero el numero de filas al patron de llenado de cada columna
                if (i - reiniciador > ((tamFilas) / Math.pow(2, columnaLlenar))) {
                    // Si se paso, a reiniciador le ponemos el valor de i - 1, para que inicie de
                    // nuevo el conteo
                    reiniciador = i - 1;
                }
                // Verifica si se debe llenar con V si no sobrepasa el patron de esa columna, si
                // sobrepasa se llena con F
                if (i - reiniciador < ((tamFilas) / Math.pow(2, columnaLlenar + 1))) {
                    tablaVerdad[i][columnaLlenar] = "V";
                } else {
                    tablaVerdad[i][columnaLlenar] = "F";
                }
            }
            // Recursividad
            llenarTabla(tablaVerdad, numeroProposiciones, tamFilas, ++columnaLlenar, ++nombreProposicion);
        }
        return tablaVerdad;
    }

    /**
     * Calcula la negacion (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param columnaResultado
     * @return
     */
    public static String[][] negacion(String[][] tablaVerdad, int columnaResultado) {
        tablaVerdad[0][columnaResultado] = "¬" + tablaVerdad[0][0];
        for (int i = 1; i < tablaVerdad.length; i++) {
            if (tablaVerdad[i][0].equals("V")) {
                tablaVerdad[i][columnaResultado] = " F";
            } else {
                tablaVerdad[i][columnaResultado] = " V";
            }
        }
        return tablaVerdad;
    }

    /**
     * Calcula la disyuncion (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param columnaResultado
     * @return
     */
    public static String[][] disyuncion(String[][] tablaVerdad, int columnaResultado) {
        tablaVerdad[0][columnaResultado] = tablaVerdad[0][0] + "v" + tablaVerdad[0][1];
        for (int i = 1; i < tablaVerdad.length; i++) {
            if (tablaVerdad[i][0].equals("V") || tablaVerdad[i][1].equals("V")) {
                tablaVerdad[i][columnaResultado] = "  V";
            } else {
                tablaVerdad[i][columnaResultado] = "  F";
            }
        }
        return tablaVerdad;
    }

    /**
     * Calcula la disyuncion (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param columnaResultado
     * @return
     */
    public static String[][] conjuncion(String[][] tablaVerdad, int columnaResultado) {
        tablaVerdad[0][columnaResultado] = tablaVerdad[0][0] + "^" + tablaVerdad[0][1];
        for (int i = 1; i < tablaVerdad.length; i++) {
            if (tablaVerdad[i][0].equals("V") && tablaVerdad[i][1].equals("V")) {
                tablaVerdad[i][columnaResultado] = "  V";
            } else {
                tablaVerdad[i][columnaResultado] = "  F";
            }
        }
        return tablaVerdad;
    }
    public static String[][] dobleImplicacion(String[][] tablaVerdad, int columnaResultado){
        tablaVerdad[0][columnaResultado] = tablaVerdad[0][0] + "<->" + tablaVerdad[0][1];
        for(int i = 1; i < tablaVerdad.length; i++){
            if(tablaVerdad[i][0].equals("V") && tablaVerdad[i][1].equals("V")){

            }else{

            }
        }
        return tablaVerdad;
    }
} 
