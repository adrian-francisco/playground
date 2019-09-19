using System;
using System.Data;

namespace ExcelReportPlay
{
    [Serializable]
    public class ReportDataSet
    {
        private readonly DataTable mDataTable;
        private readonly DateTimeInUtc mReportDataUpdatedOn;

        public ReportDataSet(DataTable dataTable, DateTimeInUtc reportDataUpdatedOn)
        {
            mDataTable = dataTable;
            mReportDataUpdatedOn = reportDataUpdatedOn;
        }

        public DataTable getDataTable()
        {
            return mDataTable;
        }

        public DateTimeInUtc getReportDataUpdatedOn()
        {
            return mReportDataUpdatedOn;
        }
    }
}