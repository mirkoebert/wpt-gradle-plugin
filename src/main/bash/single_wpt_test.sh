#! /bin/sh


maxdoccomlete=${2:-600}
url=${1?Error test url is not defined}


echo "Start WPT job with test URL: " $url
job="job.xml"
rm -f $job
source config.sh
curl -S -o $job "${wpturl}/runtest.php?url=${1}&f=xml&runs=1&video=0&web10=0&noscript=0&clearcerts=0&ignoreSSL=0&standards=0&tcpdump=0&bodies=0&continuousVideo=0&label=label-of-measurement&location=otto-exp-netlab:Firefox.Native"

xmllint --format $job --output $job
u=`xpath $job "//summaryCSV/text()"`



echo "Wait for WPT result."
sleep 55
p="page_data.csv"
rm -f $p


echo "Get data from $u"
curl -S -o $p "$u"



echo "Analyze WPT page data"
testresult=0
r="page_data_only.csv"
rm -f $r
tail -n +2 $p > $r
d=`awk -F "\"*,\"*" '{print $19}' $r `
for i in $d; do
#        echo item: $i
        if [ $i -gt $maxdoccomlete ] 
        then
                testresult=$(($testresult + 1))
        fi  
done



echo "Test Result: " $testresult
exit $testresult
