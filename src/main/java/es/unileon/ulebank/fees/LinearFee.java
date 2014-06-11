package es.unileon.ulebank.fees;



/**
 * Simple fee that applies a minimum plus a percentage of the given amount.
 * @author roobre
 */
public class LinearFee implements FeeStrategy {
	private static final long serialVersionUID = 1L;
	/**
	 * Indice de la tabla
	 */
//	@Id
//	@GeneratedValue (strategy = GenerationType.AUTO)
//	@Column (name = "id")
	private Integer id;
	/**
	 * Descripcion de la comision
	 */
//	@Column (name = "description")
	private String description;
	
    /**
     * Fee to be applied as amount multiplicator, THUS ONE (a 2% fee is storead
     * as 0.02).
     */
//	@Column (name = "commission")
    private final double fee;

    /**
     * Minimum value which is always paid.
     */
//    private final double minimum;

    public LinearFee(double fee, double minimum) throws InvalidFeeException {
        if (fee < 0 || minimum < 0) {
            throw new InvalidFeeException();
        }
        
        this.fee = fee;
//        this.minimum = minimum;
    }
    
    public LinearFee() {
    	this.fee = 0.0;
    }

    public double getFee(double value) {
        double total=this.fee * value;
//        if(total < this.minimum) {
//            total = this.minimum;
//        }
        
        return total;
    
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
