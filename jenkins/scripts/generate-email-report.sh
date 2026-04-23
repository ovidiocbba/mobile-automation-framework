#!/usr/bin/env bash

set -e

REPORT_ROOT="allure-report"
OUTPUT="email-report.html"

echo "<html><head>" > $OUTPUT

# Styles
cat <<EOF >> $OUTPUT
<style>
body { font-family: Arial; margin: 20px; }
h2 { color: #333; }
table { border-collapse: collapse; width: 100%; margin-top: 20px; }
th { background-color: #222; color: white; }
td, th { padding: 10px; text-align: center; }
.pass { color: green; font-weight: bold; }
.fail { color: red; font-weight: bold; }
.broken { color: orange; font-weight: bold; }
.summary { background: #f5f5f5; padding: 15px; border-radius: 8px; }
</style>
EOF

echo "</head><body>" >> $OUTPUT

echo "<h2> Mobile Automation Execution Report</h2>" >> $OUTPUT

# Build Info
echo "<div class='summary'>" >> $OUTPUT
echo "<b>Branch:</b> ${BRANCH}<br/>" >> $OUTPUT
echo "<b>Build:</b> <a href='${BUILD_URL}'>#${BUILD_NUMBER}</a><br/>" >> $OUTPUT
echo "<b>Execution:</b> ${EXECUTION}<br/>" >> $OUTPUT
echo "<b>Tag:</b> ${SCENARIO_TAG}<br/>" >> $OUTPUT
echo "<b>Date:</b> $(date)<br/>" >> $OUTPUT
echo "</div>" >> $OUTPUT

# Totals
TOTAL_FAILED=0
TOTAL_PASSED=0
TOTAL_TESTS=0
TOTAL_BROKEN=0
TOTAL_DURATION=0
DEVICES=""

# Table Header
echo "<table>" >> $OUTPUT
echo "<tr>
<th>Device</th>
<th>Status</th>
<th>Total</th>
<th>Passed</th>
<th>Failed</th>
<th>Broken</th>
<th>Duration</th>
<th>Report</th>
</tr>" >> $OUTPUT

for dir in ${REPORT_ROOT}/allure-*; do

  DEVICE=$(basename "$dir" | sed 's/allure-//')
  SUMMARY="$dir/widgets/summary.json"

  PASSED=$(jq '.statistic.passed' $SUMMARY)
  FAILED=$(jq '.statistic.failed' $SUMMARY)
  BROKEN=$(jq '.statistic.broken' $SUMMARY)
  TOTAL=$(jq '.statistic.total' $SUMMARY)
  DURATION_MS=$(jq '.time.duration' $SUMMARY)

  DURATION_MIN=$((DURATION_MS/1000/60))

  # Totals
  TOTAL_FAILED=$((TOTAL_FAILED + FAILED))
  TOTAL_BROKEN=$((TOTAL_BROKEN + BROKEN))
  TOTAL_PASSED=$((TOTAL_PASSED + PASSED))
  TOTAL_TESTS=$((TOTAL_TESTS + TOTAL))
  TOTAL_DURATION=$((TOTAL_DURATION + DURATION_MS))

  DEVICES="${DEVICES} ${DEVICE}"

  # Status
  if [ "$FAILED" -gt 0 ] || [ "$BROKEN" -gt 0 ]; then
    STATUS="<span class='fail'>FAILED</span>"
  else
    STATUS="<span class='pass'>PASSED</span>"
  fi

  # Report link por device
  REPORT_LINK="${BUILD_URL}Mobile-Report/allure-${DEVICE}/index.html"

  echo "<tr>
  <td>${DEVICE}</td>
  <td>${STATUS}</td>
  <td>${TOTAL}</td>
  <td class='pass'>${PASSED}</td>
  <td class='fail'>${FAILED}</td>
  <td class='broken'>${BROKEN}</td>
  <td>${DURATION_MIN} min</td>
  <td><a href='${REPORT_LINK}'>Open</a></td>
  </tr>" >> $OUTPUT

  # Failed tests
  FAILURES_FILE="$dir/widgets/failed-tests.json"

  if [ -f "$FAILURES_FILE" ]; then
    echo "<tr><td colspan='8'>" >> $OUTPUT
    echo "<details><summary> Failed Tests (${DEVICE})</summary><ul>" >> $OUTPUT

    jq -r '.items[].name' $FAILURES_FILE | while read test; do
      echo "<li>${test}</li>" >> $OUTPUT
    done

    echo "</ul></details>" >> $OUTPUT
    echo "</td></tr>" >> $OUTPUT
  fi

done

echo "</table>" >> $OUTPUT

# Global Summary
TOTAL_DURATION_MIN=$((TOTAL_DURATION/1000/60))

echo "<h3>Global Summary</h3>" >> $OUTPUT
echo "<div class='summary'>" >> $OUTPUT
echo "<b>Total Tests:</b> ${TOTAL_TESTS}<br/>" >> $OUTPUT
echo "<b>Passed:</b> <span class='pass'>${TOTAL_PASSED}</span><br/>" >> $OUTPUT
echo "<b>Failed:</b> <span class='fail'>${TOTAL_FAILED}</span><br/>" >> $OUTPUT
echo "<b>Broken:</b> <span class='broken'>${TOTAL_BROKEN}</span><br/>" >> $OUTPUT
echo "<b>Duration:</b> ${TOTAL_DURATION_MIN} min<br/>" >> $OUTPUT
echo "</div>" >> $OUTPUT

# 🔗 Links
echo "<br/><a href='${BUILD_URL}Mobile-Report/'> Open Full Report</a>" >> $OUTPUT
echo "<br/><a href='${BUILD_URL}'> Open Jenkins Build</a>" >> $OUTPUT

echo "</body></html>" >> $OUTPUT

# Subject inteligente
if [ "$TOTAL_FAILED" -gt 0 ] || [ "$TOTAL_BROKEN" -gt 0 ]; then
  SUBJECT="[FAILED] ${BRANCH} | ${TOTAL_FAILED} failed | Build #${BUILD_NUMBER}"
else
  SUBJECT="[PASSED] ${BRANCH} | ${TOTAL_TESTS} tests | Build #${BUILD_NUMBER}"
fi

echo "EMAIL_SUBJECT=$SUBJECT" > email.env
