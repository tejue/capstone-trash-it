import styled from "styled-components";

type GameBoxProps = {
    text: string;
}

export default function GameBox(props: GameBoxProps) {
    return (
        <StyledSection>
            <StyledParagraph>{props.text}</StyledParagraph>
        </StyledSection>
    )
}

const StyledSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`

const StyledParagraph = styled.p<{ $backgroundColor?: string }>`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  line-height: 1.4;
  padding: 20px;
  background-color: #E6F0E9;
  width: 300px;
  height: 300px;
  clip-path: polygon(50% 0%, 90% 20%, 100% 50%, 100% 80%, 60% 100%, 20% 90%, 0% 60%, 10% 25%);
`