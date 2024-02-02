import styled from "styled-components";

type BackgroundProps = {
    $backgroundColor?: string;
}

export const Background = styled.div<BackgroundProps>`
  content: "";
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: ${({$backgroundColor}) => $backgroundColor === "none" ? "transparent" : "rgba(76, 167, 166, 0.6)"};
  backdrop-filter: blur(0.1px);
  z-index: -1;
`;
