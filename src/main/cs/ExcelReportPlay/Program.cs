using System;
using System.Data;
using System.Diagnostics;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Timers;
using GrapeCity.ActiveReports;
using GrapeCity.ActiveReports.Document.Section;
using GrapeCity.ActiveReports.Export.Excel.Section;
using GrapeCity.ActiveReports.PageReportModel;
using DataSet = GrapeCity.ActiveReports.PageReportModel.DataSet;

namespace ExcelReportPlay
{
    public static class Program
    {
        // 32-bit
        //   30k OK
        //   40k OOM
        // 64-bit
        //   40k OK
        //   100k OK
        //   150k OK
        private const int DefaultRows = 1000;

        private static readonly DateTimeInUtc Now = DateTimeInUtc.now();
        private static readonly Random Rnd = new Random();

        public static void Main(string[] parameters)
        {
            int rows = DefaultRows;
            if (parameters.Length != 0)
            {
                rows = int.Parse(parameters[0]);
            }

            var resultDataSets = new ReportDataSet[2];
            resultDataSets[0] = new ReportDataSet(GetDataTable(rows), Now);
            resultDataSets[1] = new ReportDataSet(GetHeaderTable(), Now);

            var rdlx = new StreamReader("../../../Report.rdlx", Encoding.UTF8);

            var pageReport = new PageReport(rdlx);
            FixReport(pageReport.Report);

            LocateDataSourceEventHandler handler = (sender, args) =>
            {
                var dataSetName = args.DataSetName;
                if (dataSetName.StartsWith("ResultSet_", StringComparison.InvariantCultureIgnoreCase))
                {
                    int inx;
                    if (int.TryParse(dataSetName.Substring(10), out inx))
                    {
                        if (inx >= 0 && inx < resultDataSets.Length)
                        {
                            args.Data = resultDataSets[inx].getDataTable();
                        }
                    }
                }
            };

            pageReport.Document.LocateDataSource += handler;
            if (pageReport.Document.Parameters.Contains("ExportType"))
            {
                pageReport.Document.Parameters["ExportType"].CurrentValue = "XLS";
            }

            var xls = new XlsExport
            {
                FileFormat = FileFormat.Xlsx,
                OpenXmlStandard = OpenXmlStandard.Transitional,
                Pagination = false,
                PageSettings =
                {
                    Orientation = PageOrientation.Landscape,
                    PaperSize = PaperSizes.Tabloid
                }
            };

            var filename = "C:/Temp/report.xlsx";

            using (var outputStream = new FileStream(filename, FileMode.Create))
            {
                var stopwatch = new Stopwatch();
                stopwatch.Start();
                var timer = new Timer(5000);
                timer.Elapsed += OnTimedEvent;
                timer.Start();
                Console.WriteLine("number of rows: " + rows);
                Console.WriteLine("before export: {0:N0} bytes", GC.GetTotalMemory(false));
                xls.Export(pageReport.Document, outputStream);
                Console.WriteLine("after export: {0:N0} bytes", GC.GetTotalMemory(false));
                Console.WriteLine("file written: " + filename);
                timer.Stop();
                stopwatch.Stop();
                Console.WriteLine("elapsed time: " + stopwatch.Elapsed);
            }
        }

        private static void OnTimedEvent(object sender, ElapsedEventArgs e)
        {
            Console.WriteLine("during export: {0:N0} bytes", GC.GetTotalMemory(false));
        }

        private static DataTable GetDataTable(int rows)
        {
            var dataTable = new DataTable();
            dataTable.Columns.Add("reviewer_UserGuid");
            dataTable.Columns.Add("reviewed");
            dataTable.Columns.Add("escalated");
            dataTable.Columns.Add("identified");
            dataTable.Columns.Add("clearedIndividual");
            dataTable.Columns.Add("clearedConversationBatch");
            dataTable.Columns.Add("clearedSubjectBatch");
            dataTable.Columns.Add("clearedOtherBatch");
            dataTable.Columns.Add("confirmClosed");
            dataTable.Columns.Add("confirmFollowup");
            dataTable.Columns.Add("followupClosed");
            dataTable.Columns.Add("ignored");
            dataTable.Columns.Add("deferred");
            dataTable.Columns.Add("commented");
            dataTable.Columns.Add("reviewer");

            for (var i = 0; i < rows; i++)
            {
                var row = dataTable.NewRow();
                row["reviewer_UserGuid"] = Guid.NewGuid().ToString();
                row["reviewed"] = Rnd.Next(10).ToString();
                row["escalated"] = Rnd.Next(10).ToString();
                row["identified"] = Rnd.Next(10).ToString();
                row["clearedIndividual"] = Rnd.Next(10).ToString();
                row["clearedConversationBatch"] = Rnd.Next(10).ToString();
                row["clearedSubjectBatch"] = Rnd.Next(10).ToString();
                row["clearedOtherBatch"] = Rnd.Next(10).ToString();
                row["confirmClosed"] = Rnd.Next(10).ToString();
                row["confirmFollowup"] = Rnd.Next(10).ToString();
                row["followupClosed"] = Rnd.Next(10).ToString();
                row["ignored"] = Rnd.Next(10).ToString();
                row["deferred"] = Rnd.Next(10).ToString();
                row["commented"] = Rnd.Next(10).ToString();
                row["reviewer"] = Guid.NewGuid().ToString();
                dataTable.Rows.Add(row);
            }

//            // serialize
//            BinaryFormatter serializer = new BinaryFormatter();
//            using (FileStream fs = new FileStream("C:/Temp/serialized-x64.dat", FileMode.Create))
//            {
//                serializer.Serialize(fs, dataTable);
//            }

//            // deserialize
//            BinaryFormatter deserializer = new BinaryFormatter();
//            using (FileStream fs = new FileStream("C:/Temp/serialized-x64.dat", FileMode.Open, FileAccess.Read))
//            {
//                dataTable = (DataTable) deserializer.Deserialize(fs);
//            }

            return dataTable;
        }

        private static DataTable GetHeaderTable()
        {
            var headerTable = new DataTable();
            headerTable.Columns.Add("createdBy_UserGuid");
            headerTable.Columns.Add("createdOn");
            headerTable.Columns.Add("from");
            headerTable.Columns.Add("to");
            headerTable.Columns.Add("reviewTeam");
            headerTable.Columns.Add("jobExecutedOn");
            headerTable.Columns.Add("jobNextScheduledOn");
            headerTable.Columns.Add("jobScheduleFrequency");
            headerTable.Columns.Add("createdBy");

            var headerRow = headerTable.NewRow();
            headerRow["createdBy_UserGuid"] = Guid.NewGuid().ToString();
            headerRow["createdOn"] = Now.toVOString();
            headerRow["from"] = Now.getDateAsString();
            headerRow["to"] = Now.getDateAsString();
            headerRow["reviewTeam"] = "All";
            headerRow["jobExecutedOn"] = Now.toVOString();
            headerRow["jobNextScheduledOn"] = Now.toVOString();
            headerRow["jobScheduleFrequency"] = "";
            headerRow["createdBy"] = "Dept1User1 Dept1User1";
            headerTable.Rows.Add(headerRow);

            return headerTable;
        }

        private static void FixReport(Report report)
        {
            foreach (ReportParameter p in report.ReportParameters)
            {
                p.Hidden = true;
            }
            if (report.DataSources != null)
            {
                foreach (DataSource dsn in report.DataSources)
                {
                    if (!dsn.ConnectionProperties.DataProvider.Equals("DATASET",
                        StringComparison.InvariantCultureIgnoreCase))
                    {
                        dsn.DataSourceReference = "";
                        dsn.ConnectionProperties.DataProvider = "DATASET";
                    }
                }
            }
            if (report.DataSets != null)
            {
                foreach (DataSet d in report.DataSets)
                {
                    d.Query.CommandText = "";
                }
            }
        }
    }
}