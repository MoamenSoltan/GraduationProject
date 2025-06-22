import { useEffect, useState } from 'react';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';

const SEMESTER_ORDER = { 'Spring': 0, 'Summer': 1, 'Fall': 2 };

const StudentResults = () => {
  const axiosPrivate = useAxiosPrivate();
  const [semesters, setSemesters] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchResults = async () => {
      try {
        const response = await axiosPrivate.get('/student/semesters/degree');
        setSemesters(response.data);
        console.log("results :",response.data);
        
      } catch {
        setError('Failed to fetch results.');
      } finally {
        setLoading(false);
      }
    };
    fetchResults();
  }, [axiosPrivate]);

  if (loading) {
    return <div className="w-full text-center py-20 text-gray-500 text-xl">Loading results...</div>;
  }

  if (error) {
    return <div className="w-full text-center py-20 text-red-500 text-lg">{error}</div>;
  }

  if (!semesters.length) {
    return <div className="w-full text-center py-20 text-gray-400 text-lg">No results found.</div>;
  }

  // Sort semesters by year (desc) and then by semester order (Fall, Spring, Summer)
  const sortedSemesters = [...semesters].sort((a, b) => {
    if (b.semesterYear !== a.semesterYear) {
      return b.semesterYear - a.semesterYear;//b-a descending
    }
    return SEMESTER_ORDER[a.semesterName] - SEMESTER_ORDER[b.semesterName];//a-b ascending
  });

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <h2 className="text-2xl font-semibold text-gray-800 mb-8">Student Results</h2>
      <div className="space-y-8">
        {sortedSemesters.slice(0,sortedSemesters.length-1).map((semester, idx) => (
          <div key={idx} className="bg-white rounded-lg shadow p-6 border border-gray-100">
            <div className="flex flex-col md:flex-row md:justify-between md:items-center mb-4">
              <div className="text-lg font-bold text-blue-700">
                {semester.semesterName} {semester.semesterYear}
              </div>
              <div className="text-md text-gray-700 font-medium">
                GPA: <span className="font-bold text-green-600">{semester.gpa}</span>
              </div>
            </div>
            <div className="overflow-x-auto">
              <table className="min-w-full border border-gray-200 rounded-lg">
                <thead className="bg-gray-100">
                  <tr>
                    <th className="py-2 px-4 border-b text-left">Course Code</th>
                    <th className="py-2 px-4 border-b text-left">Course Name</th>
                    <th className="py-2 px-4 border-b text-left">Hours</th>
                    <th className="py-2 px-4 border-b text-left">Degree</th>
                  </tr>
                </thead>
                <tbody>
                  {semester.courses.map((course, cidx) => (
                    <tr key={cidx} className="hover:bg-gray-50">
                      <td className="py-2 px-4 border-b">{course.courseCode}</td>
                      <td className="py-2 px-4 border-b">{course.courseName}</td>
                      <td className="py-2 px-4 border-b">{course.hours}</td>
                      <td className="py-2 px-4 border-b">{course.degree}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        ))}
        
      </div>
      {/* Cumulative GPA Card */}
      {sortedSemesters.length > 0 && (
        <div className="mt-10 flex justify-center">
          <div className="bg-blue-100 border border-blue-300 rounded-lg shadow p-6 w-full max-w-md text-center">
            <h3 className="text-xl font-semibold text-blue-800 mb-2">Cumulative GPA</h3>
            <div className="text-3xl font-bold text-green-700">
              {sortedSemesters[sortedSemesters.length - 1].gpa}
            </div>

          </div>
        </div>
      )}
    </div>
  );
};

export default StudentResults;