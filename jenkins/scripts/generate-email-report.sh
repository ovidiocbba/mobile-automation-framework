#!/usr/bin/env bash

set -e

REPORT_ROOT="allure-report"
OUTPUT="email-report.html"

echo "<html><body style='font-family:Arial'>" > $OUTPUT
echo "<h2> Mobile Automation Execution Summary</h2>" >> $OUTPUT
echo "<table border='1' cellpadding='8' cellspacing='0'>" >> $OUTPUT
echo "<tr>
<th>Device</th>
<th>Total</th>
<th>Passed</th>
<th>Failed</th>
<th>Broken</th>
<th>Duration</th>
<th>Report</th>
</tr>" >> $OUTPUT

TOTAL_FAILED=0
DEVICES=""

for dir in ${REPORT_ROOT}/allure-*; do
  DEVICE=$(basename "$dir" | sed 's/allure-//')
  SUMMARY="$dir/widgets/summary.json"

  PASSED=$(jq '.statistic.passed' $SUMMARY)
  FAILED=$(jq '.statistic.failed' $SUMMARY)
  BROKEN=$(jq '.statistic.broken' $SUMMARY)
  TOTAL=$(jq '.statistic.total' $SUMMARY)
  DURATION_MS=$(jq '.time.duration' $SUMMARY)

  DURATION_SEC=$((DURATION_MS/1000))
  DURATION_MIN=$((DURATION_SEC/60))

  TOTAL_FAILED=$((TOTAL_FAILED + FAILED + BROKEN))
  DEVICES="${DEVICES}+${DEVICE}"

  REPORT_LINK="${BUILD_URL}Mobile-Report/"

  echo "<tr>
  <td>${DEVICE}</td>
  <td>${TOTAL}</td>
  <td style='color:green'>${PASSED}</td>
  <td style='color:red'>${FAILED}</td>
  <td style='color:orange'>${BROKEN}</td>
  <td>${DURATION_MIN} min</td>
  <td><a href='${REPORT_LINK}'>View Report</a></td>
  </tr>" >> $OUTPUT
done

echo "</table>" >> $OUTPUT
echo "<br/><a href='${BUILD_URL}'> View Jenkins Build</a>" >> $OUTPUT
echo "</body></html>" >> $OUTPUT

if [ "$TOTAL_FAILED" -gt 0 ]; then
  SUBJECT="[FAILED] Mobile Automation Report ${DEVICES}"
else
  SUBJECT="[PASSED] Mobile Automation Report ${DEVICES}"
fi

echo "EMAIL_SUBJECT=$SUBJECT" > email.env
