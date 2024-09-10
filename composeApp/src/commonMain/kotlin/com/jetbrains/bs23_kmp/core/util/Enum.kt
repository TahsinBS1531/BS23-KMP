package com.jetbrains.bs23_kmp.core.util

enum class CreditType(
    val value: Int, val displayName: String
){
    Cash(1, "Cash"),
    Regular(2, "Regular Credit"),
    CalendarMonth(3, "Calendar Month Credit"),
    SpecialCreditRule(4, "Special Credit");

    companion object {
        private val map = CreditType.values().associateBy { it.value }
        infix fun from(value: Int) = map[value]

        fun toDropdownItems(): List<DropdownItem> {
            return CreditType.values().map { DropdownItem(it.value.toString(), it.displayName) }
        }
    }
}
//
//enum class PaymentType(
//    val value: Int, val displayName: String
//) {
//    DueImmediately(0, "Due Immediately"),
//    Days10(10, "10 Days Net"),
//    Days20(20, "20 Days Net"),
//    Days30(30, "30 Days Net"),
//    Days60(60, "60 Days Net");
//
//
//    companion object {
//        private val map = PaymentType.values().associateBy { it.value }
//        infix fun from(value: Int) = map[value]
//
//        fun toDropdownItems(): List<DropdownItem> {
//            return PaymentType.values().map { DropdownItem(it.value.toString(), it.displayName) }
//        }
//    }
//
//}


enum class PaymentType(
    val value: Int, val displayName: String,
){
    DueImmediately(0, "Due Immediately"),
    Days10(10, "10 Days Net"),
    Days20(20, "20 Days Net"),
    Days30(30, "30 Days Net"),
    Days60(60, "60 Days Net"),
    Days100(100, "100 Days Net"),
    Days120(120, "120 Days Net"),
    Days15(15, "15 Days Net"),
    Days180(180, "180 Days Net"),
    Days2(2, "2 Days Net"),
    Days210(210, "210 Days Net"),
    Days25(25, "25 Days Net"),
    Days270(270, "270 Days Net"),
    Days29(29, "29 Days Net"),
    Days290(290, "290 Days Net"),
    Days3(3, "3 Days Net"),
    Days31(31, "31 Days Net"),
    Days35(35, "35 Days Net"),
    Days40(40, "40 Days Net"),
    Days45(45, "45 Days Net"),
    Days5(5, "5 Days Net"),
    Days50(50, "50 Days Net"),
    Days55(55, "55 Days Net"),
    Days7(7, "7 Days Net"),
    Days75(75, "75 Days Net"),
    Days90(90, "90 Days Net");

    companion object {
        private val map = PaymentType.values().associateBy { it.value }
        infix fun from(value: Int) = map[value]

        fun toDropdownItems(): List<DropdownItem> {
            return PaymentType.values().map { DropdownItem(it.value.toString(), it.displayName) }
        }
    }
}





enum class ReasonType(
    val value: Int,
    val displayName: String
){
    OVERDUE(1, "Overdue"),
    CREDIT_LIMIT_CROSSED(2, "Credit Limit Crossed"),
    CREDIT_LIMIT_DATE_CROSSED(3, "Credit Limit Date Crossed"),
    IN_CREDIT_LIMIT(4, "In Credit Limit"),
    IN_CASH(5, "In Cash");

    companion object {
        private val map = ReasonType.values().associateBy { it.value }
        infix fun from(value: Int) = map[value]
    }
}


enum class Shift(val value:Int){
    MORNING(1),
    EVENING(2);

    companion object {
        private val map = Shift.values().associateBy { it.value }
        infix fun from(value: Int) = map[value]
    }
}

enum class OrderPaymentType(
    val value: Int,
    val displayName: String
){
    CASH(1, "Cash"),
    CREDIT(2, "Credit");

    companion object {
        private val map = OrderPaymentType.values().associateBy { it.value }
        infix fun from(value: Int) = map[value]
    }
}


/*
CASH & CREDIT ORDER will be blocked if
1. If api/appsync/eligible-for-credit -> orderCriteriaType is RESTRICTED

CREDIT ORDER will be blocked if
1. 1-7 Days of month
2. order is lower than creditLimitAmount

 {
    "customerId": "0e788bbb-f9e9-4524-5c0a-08dc0707346b",
    "remainingAmount": 0,        -> Validate credit order limit based on this field
    "creditLimitAmount": 246540, -> Display purpose only
    "customerCode": "133149",
    "name": "AL SHAMI HOSPITAL",
    "adjustment": 0,         -> DCC adjustment
    "reasonType": 4,         -> Show reason if credit is blocked (display purpose only)
    "orderCriteriaType": 2   -> Validate OrderCriteriaType based on this field
  },


 */

enum class OrderCriteriaType(
    val value: Int,
    val displayName: String
){
    Cash(1, "Cash"),
    Credit(2, "Credit"),
    Restricted(3, "Restricted");

    companion object {
        private val map = OrderCriteriaType.values().associateBy { it.value }
        infix fun from(value: Int) = map[value]
    }
}

/*

 */