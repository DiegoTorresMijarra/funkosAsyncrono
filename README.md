# funkosAsyncrono

### Miembros del equipo

[Diego Torres Mijarra](https://github.com/DiegoTorresMijarra)

## Modelos
### Funkos
Clase Funkos que representa los funkos que se van a procesar.

``` java
public record Funkos(UUID cod, String nombre, Modelo modelo, Double precio, LocalDate FechaLanzamiento) {
    @Override
    public String toString() {
        return "Funkos{" +
                "cod=" + cod +
                ", nombre='" + nombre + '\'' +
                ", modelo=" + modelo +
                ", precio=" + precio +
                ", FechaLanzamiento=" + FechaLanzamiento +
                '}';
    }
}
```
## Controllers
En este paquete se encontraran los procesadores Csv, para obtener Funkos.

### ProcesadorCsv

``` java

/**
 * Clase que procesa un archivo csv y devuelve una lista de Funkos
 * @see Funkos
 * @see Callable
 * @author Diego
 * @version 1.0
 */

public class ProcesadorCsv implements Callable<List<Funkos>> {
    /**
     * instancia Procesador
     */
    private static ProcesadorCsv instancia;

    /**
     * path a la carpeta foco del procesador
     */
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    /**
     * nombre del archivo csv creado
     */
    private static final String csvFile="funkos.csv";

    /**
     * devuelve la instancia del procesador, si esta es null la inicia
     * @return ProcesadorCsv
     */
    public static ProcesadorCsv getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorCsv();
        }
        return instancia;
    }

```
## Enums
Directorio en el que amacenaremos las enumeraciones de la app
### Modelo
Enumeracion de los posibles valores que puede tomar el modelo de los Funkos.

``` java
public enum Modelo {
    MARVEL, DISNEY, ANIME , OTROS
}
```

## Repositories.base
Aqui se almacena la interfaz del repositorio y la implementacion de esta para procesar pokemons

### CrudRepository
Interfaz genérica para operaciones CRUD (Create, Read, Update, Delete) en una base de datos.

``` java
public interface CrudRepository<T> {

    /**
     * Encontrar todas las entidades
     * @return List Lista de entidades
     * @throws SQLException Excepcion
     */
    List<T> findAll() throws SQLException;

    /**
     * Encontrar una entidad por su id
     * @param id Id de la entidad
     * @return T Entidad
     * @throws SQLException - Excepcion
     */
    T findById(Integer id) throws SQLException;

    /**
     * Insertar una entidad
     * @param entity Entidad
     * @return T  Entidad insertada
     * @throws SQLException - Excepcion
     */
    T insert(T entity) throws SQLException;

    /**
     * Actualizar una entidad
     * @param entity  Entidad
     * @return T  Entidad actualizada
     * @throws SQLException  Excepcion
     */
    T update(T entity)  throws SQLException ;

    /**
     * Eliminar una entidad
     * @param entity  Entidad
     * @return T  Entidad eliminada
     * @throws SQLException  Excepcion
     */
    T delete(T entity)  throws SQLException ;

}
```
## Repositories.base
Aqui se almacenaran los repositorios CRUD, especificos.

### CrudFunkosRepository
Interfaz específica para operaciones CRUD (Create, Read, Update, Delete) en un repositorio de Funkos.

``` java
public interface CrudFunkosRepository extends CrudRepository <Funkos> {
    /**
     * Metodo que busca por nombres que contengan el patrón indicado.
     * @return Funkos
     */
    Funkos findByNombre() throws SQLException;

    /**
     * Metodo que inserta una lista de Funkos en la base de datos.
     * @param list a insertar
     * @return null
     * @throws SQLException
     */
    List<Funkos>insertAll(List<Funkos> list) throws SQLException;
}
```
### CrudFunkosRepository
Implementacion de CrudFunkosRepository.

``` java
public class CrudFunkosRepositoryImpl implements CrudFunkosRepository {

    /**
     * Instancia de DataBaseManager.
     */
    private final DataBaseManager dataBaseManager;

    /**
     * Constructor de la clase.
     * @param db DataBaseManager
     */
    public CrudFunkosRepositoryImpl(DataBaseManager db){
        dataBaseManager =db;
    }

    /**
     * Metodo que devuelve una lista de todos los funkos de la base de datos.
     * @return lista de funkos
     * @throws SQLException
     */
    @Override
    public List<Funkos> findAll() throws SQLException {
        dataBaseManager.openConnection();
        String sql="SELECT * FROM FUNKOS";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los funkos"));

        List<Funkos> funkosList = new ArrayList<>();

        while (result.next()){
            Funkos funko = new Funkos(
                    UUID.fromString(result.getString("cod")),
                    result.getString("nombre"),
                    Modelo.valueOf(result.getString("modelo")),
                    result.getDouble("precio"),
                    LocalDate.parse(result.getString("fecha_lanzamiento"))
            );
            funkosList.add(funko);
        }

        dataBaseManager.closeConnection();
        return funkosList;
    }

    /**
     * Metodo que devuelve un funko por su id.
     * @param id Id de la entidad
     * @return Funkos con el id dado
     * @throws SQLException
     */
    @Override
    public Funkos findById(Integer id) throws SQLException {
        dataBaseManager.openConnection();
        String sql="SELECT * FROM FUNKOS WHERE ID=?";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los funkos"));
        result.next();
        Funkos funko = new Funkos(
                UUID.fromString(result.getString("cod")),
                result.getString("nombre"),
                Modelo.valueOf(result.getString("modelo")),
                result.getDouble("precio"),
                LocalDate.parse(result.getString("fecha_lanzamiento"))
        );
        dataBaseManager.closeConnection();
        return funko;
    }

    /**
     * Metodo que inserta un funko en la base de datos.
     * @param entity Entidad
     * @return null
     * @throws SQLException
     */
    @Override
    public Funkos insert(Funkos entity) throws SQLException {
        String sql = "INSERT INTO mediciones VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?)";
        dataBaseManager.openConnection();
        dataBaseManager.insert(sql,
                null,
                entity.cod(),
                IdGenerator.getAndIncrement(),
                entity.nombre(),
                entity.modelo().toString(),
                entity.precio(),
                entity.FechaLanzamiento(),
                LocalDate.now(),
                LocalDate.now()
        );

        dataBaseManager.closeConnection();
        return null;
    }
    @Override
    public Funkos update(Funkos entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Funkos delete(Funkos entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo que devuelve un funko por su nombre.
     * @return Funkos con el nombre dado
     * @throws SQLException
     */
    @Override
    public Funkos findByNombre() throws SQLException {
        dataBaseManager.openConnection();
        String sql="SELECT * FROM FUNKOS WHERE nombre=?";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los funkos"));
        result.next();
        Funkos funko = new Funkos(
                UUID.fromString(result.getString("cod")),
                result.getString("nombre"),
                Modelo.valueOf(result.getString("modelo")),
                result.getDouble("precio"),
                LocalDate.parse(result.getString("fecha_lanzamiento"))
        );
        dataBaseManager.closeConnection();
        return funko;
    }

    /**
     * Metodo que inserta una lista de funkos en la base de datos.
     * @param list a insertar
     * @return null
     * @throws SQLException
     */
    @Override
    public List<Funkos> insertAll(List<Funkos> list) throws SQLException {
        dataBaseManager.openConnection();
        list.forEach(f-> {
            try {
                insert(f);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        dataBaseManager.closeConnection();
        return null;
    }
}
```

## Services.database
Los servicios relacionados con las conexiones de la bbdd
### DataBaseManager 
Clase que gestiona la conexión con la base de datos y las operaciones CRUD
``` java
public class DataBaseManager {

    /**
     * Instancia de la clase
     * @see DataBaseManager#getInstance()
     */
    private static DataBaseManager instance;

    /**
     * Conexión con la base de datos
     * @see Connection
     */
    private Connection connection;

    /**
     * URL de la base de datos
     * @see String
     */
    private String url;

    /**
     * Nombre de la base de datos
     * @see String
     */
    private String name;

    /**
     * URL de conexión con la base de datos
     * @see String
     */
    private String connectionUrl;

    /**
     * Driver de la base de datos
     * @see String
     */
    private String driver;


    /**
     * Sentencia preparada
     * @see PreparedStatement
     */
    private PreparedStatement preparedStatement;



    /**
     * Constructor de la clase
     */
    private DataBaseManager() {
        initConfig();
        try {
            HikariCPDataSource hikariCPDataSource=new HikariCPDataSource();
            connection = hikariCPDataSource.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Método que devuelve la instancia de la clase
     * @return DataBaseManager  Instancia de la clase
     */
    public static DataBaseManager getInstance(){
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }


    /**
     * Método que inicializa la configuración de la base de datos
     * @see Properties
     * @see FileInputStream
     * @see IOException
     * @see FileNotFoundException
     * @see RuntimeException
     */
    public void initConfig() {
        InputStream propertiesFile = ClassLoader.getSystemResourceAsStream("h2.properties");
        Properties prop = new Properties();

        try{
            prop.load(propertiesFile);
            url = prop.getProperty("database.url");
            name = prop.getProperty("database.name");
            connectionUrl = prop.getProperty("database.connectionUrl");
            driver = prop.getProperty("database.driver");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Método que abre la conexion a la base de datos
     * @see SQLException
     * @throws SQLException Error al abrir la conexion
     */
    public void openConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        connection = DriverManager.getConnection(connectionUrl);
    }

    /**
     * Método que cierra la conexion a la base de datos
     * @see SQLException
     */
    public void closeConnection() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Método que ejecuta una consulta a la base de datos
     * @see ResultSet
     * @see SQLException
     * @param querySQL Consulta a la base de datos
     * @param params Parametros de la consulta
     * @return ResultSet Resultado de la consulta
     */
    private ResultSet executeQuery(String querySQL, Object... params) throws SQLException {
        this.openConnection();
        preparedStatement = connection.prepareStatement(querySQL);
        // Vamos a pasarle los parametros usando preparedStatement
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Método que ejecuta una consulta select a la base de datos
     * @see ResultSet
     * @see SQLException
     * @param querySQL Consulta a la base de datos
     * @param params Parametros de la consulta
     * @return ResultSet Resultado de la consulta
     * @throws SQLException Error al ejecutar la consulta select
     */
    public Optional<ResultSet> select(String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    /**
     * Método que ejecuta una consulta insert a la base de datos
     * @see ResultSet
     * @see SQLException
     * @param insertSQL Consulta a la base de datos
     * @param params Parametros de la consulta
     * @throws SQLException Error al ejecutar la consulta insert
     */
    public void insert(String insertSQL, Object... params) throws SQLException {

        preparedStatement = connection.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
    }


    /**
     * Metodo que ejecuta un archivo sql para inicializar la base de datos
     * @see ScriptRunner
     * @see BufferedReader
     * @see FileReader
     * @see PrintWriter
     * @see SQLException
     * @see FileNotFoundException
     * @param sqlFile path archivo sql
     * @param logWriter Indica si se escribe el log
     * @throws FileNotFoundException Error al encontrar el archivo sql
     * @throws SQLException Error al ejecutar el archivo sql
     */
    public void initData(String sqlFile, boolean logWriter) throws FileNotFoundException, SQLException {
        this.openConnection();
        var sr = new ScriptRunner(connection);
        var reader = new BufferedReader(new FileReader(sqlFile));
        if (logWriter) {
            sr.setLogWriter(new PrintWriter(System.out));
        } else {
            sr.setLogWriter(null);
        }
        sr.runScript(reader);
    }
}
```

### HikariCPDataSource
Clase que gestiona la conexión con la base de datos y las operaciones CRUD

``` java
public class HikariCPDataSource {
      private HikariConfig config ;
      HikariDataSource dataSource ;

    /**
     * Constructor de la clase
     * @see HikariCPDataSource#HikariCPDataSource()
     */
    HikariCPDataSource() {
        config = new HikariConfig("hikari.properties");
        dataSource =new HikariDataSource(config);
    }
}
```

## RESOURCES

Como recursos nos encontraremos los siguientes archivos.

### h2.properties

Datos necesarios para una correcta conexión a la base de datos con el driver h2

```properties
database.url=jdbc:h2:./funkos;DB_CLOSE_DELAY=-1;INIT=runscript from '/init.sql'
database.driver=org.h2.Driver
database.username=diego
database.password=password
database.initTables=true
database.initScript=init.sql
```
### h2.properties

Datos necesarios para una correcta conexión a la base de datos con HikariCP

```properties
dataSource.jdbcUrl=jdbc:h2:tcp://localhost:9092/default;DB_CLOSE_DELAY=-1
dataSource.username=diego
dataSource.password=password
dataSource.poolName=funkosPool
dataSource.databaseName=funkos
dataSource.maxLifetime=1800000
dataSource.maximumPoolSize=10
```


## PROBLEMAS EN EL DESARROLLO DEL EJERCICIO
- No esta terminado, me he atascado y frustrado mucho. Porque no he sabido resolver una excepcion que me ha limitado poder avanzar en el resto de la app. 
- He pospuesto la finalizacion de la app, porque creo que necesito repasar mas los conceptos vistos en clase, para avanzar en este trabajo.
- La excepcion que sale, es"  java.lang.RuntimeException: Fatal exception during proxy generation"
- He localizado de donde proviene, pero no como solventarla.