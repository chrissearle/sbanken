import Transaction from "@/types/Transaction";

export default interface TransactionList {
  availableItems: number;
  items: Transaction[];
}
