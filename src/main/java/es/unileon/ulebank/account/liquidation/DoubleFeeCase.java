package es.unileon.ulebank.account.liquidation;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.time.Time;

public class DoubleFeeCase implements AbstractFeeCase<Double> {

    /**
     * The feature
     */
    private final Features<Double> features;
    /**
     * The formulate in String format
     */
    private final String amountFormula;
    /**
     * The triggering conditions
     */
    private final StringBuilder triggeringConditions;
    /**
     * subject for the transactions
     */
    private final String subject;
    /**
     * The evaluator for the amount formula
     */
    private static final DoubleEvaluator EVALUATOR = new DoubleEvaluator();
    /**
     * Precision in the comparations ( 10^(-10))
     */
    private static final double PRECISION = 10;
    /**
     * EPSILON value, precision
     */
    private static final double EPSILON = Math
            .pow(10, -DoubleFeeCase.PRECISION);
    /**
     * Class logger
     */
    private static final Logger LOG = Logger.getLogger(DoubleFeeCase.class);

    /**
     * Create a new Double fee Case
     * 
     * @param features
     *            (Features to use)
     * @param amountFormula
     *            ( amount formulate)
     * @param subject
     *            ( subject for the transactions)
     * @param account
     */
    public DoubleFeeCase(Features<Double> features, String amountFormula,
            String subject, Account account) {
        this.features = features;
        this.triggeringConditions = new StringBuilder();
        this.amountFormula = amountFormula;
        this.subject = subject;
    }

/**
     * Add a new condition
     * 
     * @param leftOperand (left operand)
     * @param comparator
     *            (comparator, ie, '<' or '>' )
     * @param rightOperand
     *            ( right operand)
     * @throws InvalidCondition
     *             ( if the condition isn't valid)
     */
    public void addConditionEquation(String leftOperand, char comparator,
            String rightOperand) throws InvalidCondition {
        final String leftOperandRaw = leftOperand.replace(" ", "");
        final String rightOperandRaw = rightOperand.replace(" ", "");
        if (DoubleFeeCase.isValidComparator(comparator)
                && this.testValidOperand(leftOperandRaw)
                && this.testValidOperand(rightOperandRaw)) {
            if (this.triggeringConditions.length() > 0) {
                this.triggeringConditions.append("&&");
            }
            this.triggeringConditions.append(DoubleFeeCase.generateEquation(
                    leftOperandRaw, comparator, rightOperandRaw));
        } else {
            throw new InvalidCondition("Invalid condition");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.unileon.ulebank.account.liquidation.AbstractFeeCase#triggerCase()
     */
    @Override
    public boolean triggerCase() {
        if (this.triggeringConditions.length() > 0) {
            return MVEL.evalToBoolean(this.triggeringConditions.toString(),
                    this.features.generateRandomFeatures());
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.account.liquidation.AbstractFeeCase#calculateAmount()
     */
    @Override
    public Transaction calculateAmount() throws TransactionException {
        Transaction result = null;
        if (this.triggerCase()) {
            final StringBuilder err = new StringBuilder();
            try {
                final double amount = DoubleFeeCase.EVALUATOR.evaluate(
                        this.amountFormula,
                        DoubleFeeCase.castTo(this.features.getFeatures()));
                result = new GenericTransaction(amount, new Date(Time
                        .getInstance().getTime()), this.subject);
            } catch (final RuntimeException r) {
                err.append(r);
            }
            if (err.length() > 0) {
                throw new TransactionException(err.toString());
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.unileon.ulebank.account.liquidation.AbstractFeeCase#getFeatures()
     */
    @Override
    public Features<Double> getFeatures() {
        return this.features;
    }

    private static String generateEquation(String leftOperand, char comparator,
            String rightOperand) {
        return "((" + leftOperand + " - " + rightOperand + ")" + comparator
                + " " + DoubleFeeCase.EPSILON + ")";
    }

    private static boolean isValidComparator(char comparator) {
        return (comparator == '<') || (comparator == '>');
    }

    private static StaticVariableSet<Double> castTo(Map<String, Double> map) {
        final Set<String> keys = map.keySet();
        final StaticVariableSet<Double> set = new StaticVariableSet<Double>();
        for (final String key : keys) {
            set.set(key, map.get(key));
        }
        return set;
    }

    /**
     * Test if a condition is valid
     * 
     * @param operand
     *            ( Operand to test)
     * @return ( true if success, otherwise false )
     * @throws InvalidCondition
     *             ( if the condition isn't valid)
     */
    private boolean testValidOperand(String operand) throws InvalidCondition {
        try {
            DoubleFeeCase.EVALUATOR.evaluate(operand, DoubleFeeCase
                    .castTo(this.features.generateRandomFeatures()));
        } catch (final RuntimeException e) {
            DoubleFeeCase.LOG.error(e);
            throw new InvalidCondition(e.toString());
        }
        return true;
    }

}
