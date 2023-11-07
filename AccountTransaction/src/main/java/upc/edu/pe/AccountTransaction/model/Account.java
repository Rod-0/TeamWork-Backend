package upc.edu.pe.AccountTransaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una entidad de la tabla accounts de la base de datos en la aplicacion
 * <p>
 *     Las anotaciones utilizadas en esta clase son:
 *     <ul>
 *         <li>{@code @Data}: Esta anotacion de Lombok genera los metodos getters y setters de los atributos de la clase</li>
 *         <li>{@code @Builder}: Esta anotacion de Lombok genera un constructor con todos los parametros de la clase</li>
 *         <li>{@code @NoArgsConstructor}: Esta anotacion de Lombok genera un constructor vacio</li>
 *         <li>{@code @AllArgsConstructor}: Esta anotacion de Lombok genera un constructor con todos los parametros de la clase</li>
 *         <li>{@code @Entity}: Esta anotacion de JPA indica que la clase es una entidad</li>
 *         <li>{@code @Table}: Esta anotacion de JPA indica el nombre de la tabla de la base de datos que representa la entidad</li>
 *     </ul>
 * @author Rodrigo Pozo and Andrea O'Higgins
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_customer", nullable = false, length = 30)
    private String nameCustomer;
    @Column(name = "number_account", length = 13, nullable = false)
    private String numberAccount;

}
