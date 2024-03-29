import styled from "styled-components";

type GameBoxProps = {
    $text: string;
    color?: string;
    $backgroundColor?: string;
    size?: string;
    $margin?: string;
}

export default function GameBox(props: Readonly<GameBoxProps>) {
    return (
        <StyledParagraph {...props}>{props.$text}</StyledParagraph>
    )
}

const StyledParagraph = styled.p<GameBoxProps>`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  line-height: 1.4;
  padding: 20px;
  ${({$margin}) => `${$margin} &&  ${$margin}`};
  color: ${({color}) => color === "colorLight" ? "var(--text-color-light)" : "var(--text-color-dark)"};
  background-color: ${({$backgroundColor}) => $backgroundColor || "var(--secondary-color)"};
  width: ${({size}) => size || "300px"};
  height: ${({size}) => size || "300px"};
  clip-path: polygon(50% 0%, 90% 20%, 100% 50%, 100% 80%, 60% 100%, 20% 90%, 0% 60%, 10% 25%);
`