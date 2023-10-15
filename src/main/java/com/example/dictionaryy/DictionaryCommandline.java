package com.example.dictionaryy;

import static com.example.dictionaryy.DictionaryManagement.insertFromCommandline;

public class DictionaryCommandline {
    public void swapWord(Word a,Word b)
    {
        String tempWt = a.getWord_target();
        a.setWord_target(b.getWord_target());
        b.setWord_target(tempWt);

        String tempWe = a.getWord_explain();
        a.setWord_explain(b.getWord_explain());
        b.setWord_explain(tempWe);
    }
    public Boolean compareWord(Word a,Word b)
    {
        int aSize = a.getWord_target().length();
        int bSize = b.getWord_target().length();
        String wtb = b.getWord_target();
        String wta = a.getWord_target();
        while(aSize>bSize)
        {
            wtb = wtb + ' ';
            bSize++;
        }
        while(aSize<bSize)
        {
            wta = wta + ' ';
            aSize++;
        }
        for (int i=0;i<wta.length();i++)
        {
            if (wta.charAt(i)>wtb.charAt(i))
            {
                return true;
            }
        }
        return false;
    }
    public void ShowAllWords(Dictionary dict){
        System.out.println("No   | English    | Vietnamese");
        int n = dict.getList().size();
        for (int i=0;i<n;i++)
        {
            int mindex = i;
            for (int j=i+1;j<n;j++)
            {
                if (compareWord(dict.getList().get(i),dict.getList().get(j)))
                {
                    mindex = j;
                }
            }
            swapWord(dict.getList().get(i),dict.getList().get(mindex));
        }
        for (int i=0;i<n;i++)
        {
            System.out.println(i+"\t" +dict.getList().get(i).getWord_target()+"         "+dict.getList().get(i).getWord_explain());
        }
    }

    public void dictionaryBasic(Dictionary dict)
    {
        insertFromCommandline(dict);
        ShowAllWords(dict);
    }

    public void dictionarySearcher(String find)
    {
        boolean canFind = false;
        Dictionary dict = new Dictionary();
        DictionaryManagement.insertFromFile(dict);
        int n = find.length();
        int nDict = dict.getList().size();
        for (int i = 0;i<nDict;i++)
        {
            boolean check = true;
            for(int j = 0;j<n;j++)
            {
                if(dict.getList().get(i).getWord_target().charAt(j)!=find.charAt(j))
                {
                    check = false;
                    break;
                }
            }
            if (check)
            {
                canFind = true;
                System.out.println(dict.getList().get(i).getWord_target()+"/t"+dict.getList().get(i).getWord_explain()+"\n");
            }
        }
        if (!canFind)
        {
            System.out.println("ko tìm được từ bạn muốn trong từ điển");
        }
    }

    public static void main(String[] args) {
        Dictionary d1 = new Dictionary();
        DictionaryCommandline dc = new DictionaryCommandline();
        dc.dictionaryBasic(d1);

    }
}
