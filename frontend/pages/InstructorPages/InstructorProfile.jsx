import { useState, useEffect } from 'react';
import toast from 'react-hot-toast';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import { BsFillPersonFill } from 'react-icons/bs';
import { LuBookText } from 'react-icons/lu';
import { useStateContext } from '../../contexts/ContextProvider';

const InstructorProfile = () => {
  const axiosPrivate = useAxiosPrivate();
  const { setAuth } = useStateContext();
  const [profileData, setProfileData] = useState({});
  const [updatedProfileData, setUpdatedProfileData] = useState({});
  const [isChanged, setIsChanged] = useState(false);
  const [loading, setLoading] = useState(false);

  let image = updatedProfileData?.imagePreview
    ? updatedProfileData.imagePreview
    : profileData?.personalImage;

  const handleChange = (e) => {
    const { value, name } = e.target;
    setUpdatedProfileData((prev) => ({ ...prev, [name]: value }));
    setIsChanged(true);
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setUpdatedProfileData((prev) => ({
      ...prev,
      personalImage: file,
      imagePreview: URL.createObjectURL(file),
    }));
    setIsChanged(true);
  };

  const fetchProfile = async () => {
    try {
      const response = await axiosPrivate.get("/instructor/profile");
      setProfileData(response.data);
      setUpdatedProfileData((prev) => ({ ...prev, ...response.data }));
    } catch {
      toast.error("An error occurred");
    } finally {
      setLoading(false);
    }
  };

  const handleSave = async (e) => {
    e.preventDefault();

    const formdata = new FormData();

    ["firstName", "lastName", "email", "bio"].forEach((key) => {
      const value = updatedProfileData[key] ?? profileData[key] ?? "";
      formdata.append(key, value);
    });

    // Handle image logic
    if (updatedProfileData.personalImage instanceof File) {
      formdata.append("personalImage", updatedProfileData.personalImage);
    } else if (profileData.personalImage && typeof profileData.personalImage === "string") {
      try {
        const imageResponse = await fetch(profileData.personalImage);
        const imageBlob = await imageResponse.blob();
        formdata.append("personalImage", imageBlob, "profile.jpg");
      } catch {
        toast.error("Could not include existing image");
      }
    }

    try {
      const response = await axiosPrivate.patch("/instructor/profile", formdata, {
        headers: { "Content-Type": "multipart/form-data" },
        withCredentials: true,
      });

      toast.success("Profile updated successfully");
       fetchProfile();
      setAuth((prev) => ({ ...prev, ...updatedProfileData }));
      setIsChanged(false);
    } catch {
      toast.error("Failed to update profile");
    }
  };

  useEffect(() => {
    if (loading) return;
    setLoading(true);
    fetchProfile();
    // eslint-disable-next-line
  }, []);

  return (
    <form onSubmit={handleSave}>
      <div className="w-full md:w-[80%] flex flex-col xl:flex-row gap-10 justify-center p-4 m-auto">
        <div className="w-full xl:w-[45%] ">
          <div className="p-6 bg-[#f6f8fa] flex flex-row space-y-3 space-x-8 rounded-lg">
            <img
              src={image}
              className="rounded-full w-24 h-24 border-gray-300 border-4"
              alt="profile photo"
            />
            <div>
              <h2 className="sub-text-2">Your Photo</h2>
              <h3 className="sub-text">This will be displayed on your profile</h3>
              <input
                type="file"
                name="personalImage"
                className="textField mt-3 file:mr-4 file:rounded-full file:border-0 file:bg-[] file:px-4 file:py-2 file:text-sm file:font-semibold file:text-black hover:file:text-white hover:file:scale-105 hover:file:transition-all hover:file:cursor-pointer hover:file:bg-[#0096C1]"
                onChange={handleFileChange}
              />
            </div>
          </div>

          <div className="mt-10 p-6 bg-[#f6f8fa] flex flex-col space-y-3 rounded-lg">
            <div className="flex flex-row gap-4">
              <BsFillPersonFill className="text-2xl" />
              <h2 className="sub-text-2">Personal Info</h2>
            </div>
            <div>
              <label htmlFor="Fname" className="sub-text">First name</label>
              <input
                type="text"
                name="firstName"
                value={updatedProfileData?.firstName ?? profileData?.firstName ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Fname"
              />

              <label htmlFor="Lname" className="sub-text">Last name</label>
              <input
                type="text"
                name="lastName"
                value={updatedProfileData?.lastName ?? profileData?.lastName ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Lname"
              />

              <label htmlFor="Email" className="sub-text">Email</label>
              <input
                type="email"
                name="email"
                value={updatedProfileData?.email ?? profileData?.email ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Email"
              />

              <label htmlFor="Bio" className="sub-text">Bio</label>
              <textarea
                name="bio"
                value={updatedProfileData?.bio ?? profileData?.bio ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Bio"
                rows={3}
              />
            </div>

            <button
              type="submit"
              disabled={!isChanged}
              className={`mt-4 p-3 rounded-lg w-full font-semibold transition-all ${
                isChanged
                  ? "bg-blue-500 text-white hover:bg-blue-600"
                  : "bg-gray-300 text-gray-500 cursor-not-allowed"
              }`}
            >
              Save Changes
            </button>
          </div>
        </div>

        <div className="w-full xl:w-[45%]">
          <div className="p-6 bg-[#f6f8fa] flex flex-col space-y-12 rounded-lg">
            <div className="flex flex-row gap-4">
              <LuBookText className="text-2xl" />
              <h2 className="sub-text-2">Department & Courses</h2>
            </div>
            <div>
              <label htmlFor="Department" className="sub-text">Department</label>
              <input
                disabled
                type="text"
                name="department"
                value={profileData?.department?.departmentName || ""}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="Courses" className="sub-text">Courses</label>
              <div className="textfield w-full min-h-11 bg-white my-3 border-gray-300 border-[1px] rounded-md flex flex-wrap gap-2 p-2">
                {profileData?.courses?.length > 0 ? (
                  profileData.courses.map((course, index) => (
                    <div
                      key={index}
                      className="px-2 rounded-lg bg-blue-100 gap-2"
                    >
                      <span className="font-light truncate">
                        {course.courseName} ({course.courseCode})
                      </span>
                    </div>
                  ))
                ) : (
                  <h1 className="sub-text">none</h1>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};

export default InstructorProfile;
