//package com.playground.rnd.discount;
//
//import com.playground.rnd.jexlValidators.JexlValidator;
//import com.playground.rnd.models.Customer;
//import com.playground.rnd.models.TransactionRecord;
//import jakarta.annotation.Nullable;
//import org.apache.logging.log4j.util.Strings;
//
//import java.util.Map;
//
//public class TestClass {
//
//    public void compute(Object config, TransactionRecord transaction){
//        var discount = 0.0;
//        var finalAmount = transaction.getAmount();
//        if(transaction.getAmount() > {config.discountableAmount} && transaction.getTransactionType().equals("WEB")){
//            // calculate the discount and subtract from amount
//            discount = ({config.discountPercentage} * transaction.amount)/100;
//        }
//        finalAmount = transaction.getAmount() - discount;
//    }
//
//    /**
//     * Initial Phase Logic for cash bash.
//     * To extend this for reviewed phase, code change is required.
//     * @param customer
//     * @return
//     */
//    public boolean canReceiveCashBack(Customer customer){
//        if(customer.getMembershipType().equals(config.MemberShipForCashBack)){
//            return true;
//        }
//        return  false;
//    }
//
//    /**
//     * This is can be called by
//     * @param customerExpression
//     */
//    public void setCashBackConfig(@Nullable String customerExpression, Map<String, Object> paramContext){
//        var cashBackConfig = getConfig();
//        if(Strings.isBlank(customerExpression)){
//            var expressionDefault = "(customer.membershipType == GOLD)";
//            cashBackConfig.setExpression(expressionDefault);
//        }else{
//            cashBackConfig.setExpression(customerExpression);
//        }
//        this.contextMapper.update(paramContext);
//        JexlValidator.validateExpression(cashBackConfig.getExpression, this.contextMapper.getCashBackContext);
//    }
//
//    public boolean canReceiveCashBack(Object config, Customer customer){
//        if(customer.getMembershipType().equals(config.MemberShipForCashBack)){
//            return true;
//        }
//        return  false;
//    }
//}
