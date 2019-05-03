import random
from pyNTCIREVAL import Labeler
from pyNTCIREVAL.metrics import nERR, QMeasure, MSnDCG, nDCG

# pip install pyNTCIREVAL
# or pip3 install pyNTCIREVAL


def data_process(y_pred, y_true):
    qrels = {}
    ranked_list = []
    c = list(zip(y_pred, y_true))
    # print(c)
    random.shuffle(c)
    c = sorted(c, key=lambda x:x[0], reverse=True)
    for i in range(len(c)):
        qrels[i] = c[i][1]
        ranked_list.append(i)
    grades = range(1, label_range+1)

    labeler = Labeler(qrels)
    labeled_ranked_list = labeler.label(ranked_list)
    rel_level_num = len(grades)
    xrelnum = labeler.compute_per_level_doc_num(rel_level_num)
    return xrelnum, grades, labeled_ranked_list

def n_dcg(y_pred, y_true, k):
    xrelnum, grades, labeled_ranked_list = data_process(y_pred, y_true)
    metric = nDCG(xrelnum, grades, cutoff=k, logb=2)
    result = metric.compute(labeled_ranked_list)
    return result


def q_measure(y_pred, y_true, k, beta=1.0):
    xrelnum, grades, labeled_ranked_list = data_process(y_pred, y_true)
    metric = QMeasure(xrelnum, grades, beta, cutoff=k)
    result = metric.compute(labeled_ranked_list)
    return result


def n_err(y_pred, y_true, k):
    xrelnum, grades, labeled_ranked_list = data_process(y_pred, y_true)
    metric = nERR(xrelnum, grades, cutoff=k)
    result = metric.compute(labeled_ranked_list)
    return result


class Item(object):
    """docstring for Item"""
    def __init__(self,s):
        super(Item, self).__init__()
        self.qid = int(s[0:2])
        self.doc = s[2:-1].strip()
        self.score = int(s[-1])
        # print(self.qid,len(self.doc),self.score)
        
class ItemTest(object):
    """docstring for ItemTest"""
    def __init__(self, s):
        super(ItemTest, self).__init__()
        self.qid = int(s[0:4])
        self.doc = s[7:7+66+1].strip()
        self.score = float(s[7+66+4:-4])
        # print(self.qid,self.doc,self.score)
        

class CMD(object):
    """docstring for CMD"""
    def __init__(self):
        super(CMD, self).__init__()
        self.DataBase = []
        self.res = []

    def findItem(self,qid,doc):
        for x in self.DataBase:
            if(x.qid == qid) and (x.doc == doc):
                return(x.score)

        return(0)

    def build_Base(self,filename):
        f=open(filename)
        for line in f:
            if (str(line[0]).isdigit()):
                tempItem = Item(line.strip())
                self.DataBase.append(tempItem)
        f.close()

    def test(self,filename):
        f=open(filename)

        y_true = []
        y_pred = []
        for line in f:
            if (str(line[0]).isdigit()):
                tempItem = ItemTest(line.strip())
                y_true.append(self.findItem(tempItem.qid,tempItem.doc))
                y_pred.append(tempItem.score)
        f.close()
        print (round(n_dcg(y_pred, y_true, k=20),3),'n_dcg     k = 20')  # y_pred: 预测的分数, y_true: 对应的relevance, k: cutoff
        print (round(q_measure(y_pred, y_true, k=20),3),'q_measure k = 20')
        print (round(n_err(y_pred, y_true, k=20),3),'n_err     k = 20')

        print (round(n_dcg(y_pred, y_true, k=10),3),'n_dcg     k = 10')  # y_pred: 预测的分数, y_true: 对应的relevance, k: cutoff
        print (round(q_measure(y_pred, y_true, k=10),3),'q_measure k = 10')
        print (round(n_err(y_pred, y_true, k=10),3),'n_err     k = 10')

        print (round(n_dcg(y_pred, y_true, k=5),3),'n_dcg     k = 5')  # y_pred: 预测的分数, y_true: 对应的relevance, k: cutoff
        print (round(q_measure(y_pred, y_true, k=5),3),'q_measure k = 5')
        print (round(n_err(y_pred, y_true, k=5),3),'n_err     k = 5')




myCMD = CMD()
label_range = 4
myCMD.build_Base("Data/ntcir14_test_label.txt")
myCMD.test("res_20.txt")

