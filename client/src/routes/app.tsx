"use client";

import { useEffect, useState } from "react";
import { useAuth } from "@/context/auth-provider";

type Post = {
  id: number;
  content: string;
  createdAt: string;
  creatorUsername: string;
  numberLikes: number;
};


function App() {
  const [posts, setPosts] = useState<Post[]>([]);
  const { isAuth, setAuth } = useAuth();

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/post", {
          credentials: "include",
        });

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();

        setPosts(data);
      } catch (error) {
        console.error("Error fetching posts:", error);
        setAuth(false)
      }
    };

    fetchPosts();
  });

  return (
    <div className="grow w-xl flex text-center flex-col gap-4 my-2.5">
    {isAuth &&
      posts.map((post) => (
        <div
          key={post.id}
          className="w-auto border rounded-lg p-4 text-left shadow-sm"
        >
          <p className="font-semibold">@{post.creatorUsername}</p>
          <p className="my-2">{post.content}</p>

          <div className="text-sm text-gray-500 flex justify-between">
            <span>{new Date(post.createdAt).toLocaleString()}</span>
            <span>❤️ {post.numberLikes}</span>
          </div>
        </div>
    ))}
    {!isAuth && (
      <div className="my-auto">
        <h1 className="text-2xl font-semibold">Welcome to Nocturno</h1>
        <p>
          Nocturno é uma rede social para compartilhar seu dia a dia, conhecer
          novas pessoas, criar laços genuínos e se conectar por meio de conversas.
        </p>
      </div>
    )}
  </div>
  );
}

export default App;
