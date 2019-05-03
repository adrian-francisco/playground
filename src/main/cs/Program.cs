using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net.NetworkInformation;
using System.Text;
using System.Threading.Tasks;

namespace Playground
{
    class Program
    {
        public static void Main(string[] args)
        {
            Program program = new Program();
            
            var str1 = new string[] {"1", "2", "3"};
            var str2 = new string[] {"2", "3", "4"};

            var int1 = new int[] {1, 2, 3};
            var int2 = new int[] {2, 3};

            Debug.WriteLine(program.AreArrayLengthsSame(str1, str2));
            Debug.WriteLine(program.AreArrayLengthsSame(int1, int2));
        }

        private bool AreArrayLengthsSame<T>(T[] array1, T[] array2)
        {
            if (array1 == null && array2 == null)
            {
                return true;
            }
            if (array1 == null || array2 == null)
            {
                return false;
            }
            return array1.Length == array2.Length;
        }
    }
}
