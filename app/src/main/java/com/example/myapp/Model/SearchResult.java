package com.example.myapp.Model;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-6
 * Time: 上午11:02
 * Email: yanru.zhang@renren-inc.com
 */
public class SearchResult {
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public ArrayList<Document> documents = new ArrayList<Document>();
    public ArrayList<TaskModel> taskModels = new ArrayList<TaskModel>();
    public ArrayList<Member> members = new ArrayList<Member>();

    public SearchResult() {
        for (int i=0;i<5;i++){
            Transaction transaction = new Transaction();
            transaction.transactionId = i;
            transaction.transactionName = "Transaction" + i;
            transactions.add(transaction);
            Document document = new Document();
            document.documentId = i;
            document.documentName = "Document" + i;
            documents.add(document);
            TaskModel taskModel = new TaskModel();
            taskModel.taskId = i;
            taskModel.taskName = "Task" + i;
            taskModels.add(taskModel);
            Member member = new Member();
            member.tmemberId = i;
            member.memberName = "Members" + i;
            members.add(member);
        }
    }
}
